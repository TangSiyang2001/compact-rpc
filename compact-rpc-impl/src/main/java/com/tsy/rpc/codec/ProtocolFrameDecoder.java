package com.tsy.rpc.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 定长解码器 在MessageSharableCodec之前使用，处理粘包半包问题
 *
 * @author Steven.T
 * @date 2022/2/19
 */
@Slf4j
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    public ProtocolFrameDecoder() {
        super(1024 * 1024 * 1024,12,4,0,0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
