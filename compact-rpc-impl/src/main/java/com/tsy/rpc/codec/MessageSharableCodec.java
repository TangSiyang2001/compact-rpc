package com.tsy.rpc.codec;

import com.tsy.rpc.base.compress.Compressor;
import com.tsy.rpc.base.compress.CompressType;
import com.tsy.rpc.base.exception.CodecException;
import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.message.Message;
import com.tsy.rpc.base.serialize.CodecType;
import com.tsy.rpc.base.serialize.Serializer;
import com.tsy.rpc.config.RpcConfig;
import com.tsy.rpc.constant.Constant;
import com.tsy.rpc.message.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 4B  magic code（魔法数）   1B version（版本）1B messageType（消息类型） 1B compress（压缩类型）
 * 1B codec（序列化类型）  4B  requestId（请求的Id）  4B full length（消息长度）
 *
 * @author Steven.T
 * @date 2022/2/19
 */
public class MessageSharableCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        final ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(Constant.MAGIC_CODE_CONTENT);
        buffer.writeByte(Constant.PROTOCOL_VERSION);
        buffer.writeByte(msg.getType());
        //TODO:尝试实现可选的压缩方式
        final byte compressType = CompressType.GZIP.getType();
        buffer.writeByte(compressType);
        final byte codecType = CodecType.getCodecType(RpcConfig.getCodecAlgorithm());
        buffer.writeByte(codecType);
        buffer.writeInt(msg.getSequenceId());
        //TODO:可以判断是否为心跳
        final byte[] serializeRes = serialize(codecType, msg);
        final byte[] compressedData = compress(compressType, serializeRes);
        buffer.readBytes(compressedData);
        out.add(buffer);
    }

    private byte[] serialize(byte codecType,Message msg){
        final Serializer serializer = loadSerializerByType(codecType);
        return serializer.serialize(msg);
    }

    private byte[] compress(byte compressType,byte[] bytes){
        final Compressor compressor = loadCompressByType(compressType);
        return compressor.compress(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        checkMagicCode(msg);
        checkVersion(msg);
        final byte messageType = msg.readByte();
        final byte compressType = msg.readByte();
        final byte codecType = msg.readByte();
        final int requestId = msg.readInt();
        final int length = msg.readInt();
        byte[] text = new byte[length];
        msg.readBytes(text);
        final byte[] content = deCompress(text, compressType);
        final Message message = deSerialize(content, codecType, messageType);
        checkSequenceId(requestId, message.getSequenceId());
        out.add(message);

    }

    private void checkMagicCode(ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("buf should be not null");
        }
        final byte[] magicCodeContent = Constant.MAGIC_CODE_CONTENT;
        final int length = magicCodeContent.length;
        byte[] magicCode = new byte[length];
        buf.readBytes(magicCode);
        for (int i = 0; i < length; i++) {
            if (magicCode[i] != magicCodeContent[i]) {
                throw new CodecException("Magic code is wrong");
            }
        }
    }

    private void checkVersion(ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("buf should be not null");
        }
        byte version = buf.readByte();
        if (version != Constant.PROTOCOL_VERSION) {
            throw new CodecException("Version does not adapt");
        }
    }

    private byte[] deCompress(byte[] content, byte compressType) {
        final Compressor compressor = loadCompressByType(compressType);
        return compressor.decompress(content);
    }

    private Message deSerialize(byte[] content, byte codecType, byte messageType) {
        final Serializer serializer = loadSerializerByType(codecType);
        return serializer.deserialize(content, MessageType.getMessageClass(messageType));
    }

    private void checkSequenceId(int requestId, int sequenceId) {
        if (requestId != sequenceId) {
            throw new CodecException("Sequence id is wrong.");
        }
    }

    private Compressor loadCompressByType(byte compressType){
        return ExtensionLoader.getExtensionLoader(Compressor.class)
                .getExtension(CompressType.getCompressName(compressType));
    }

    private Serializer loadSerializerByType(byte codecType){
        return ExtensionLoader.getExtensionLoader(Serializer.class)
                .getExtension(CodecType.getCodecName(codecType));
    }

}
