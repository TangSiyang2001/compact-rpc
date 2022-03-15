package com.tsy.rpc.base.compress;

import com.tsy.rpc.base.exception.CompressorException;
import com.tsy.rpc.base.extension.annotation.SPI;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@SPI
public interface Compressor {

    /**
     * 压缩
     *
     * @param bytes 压缩对象
     * @return 压缩结果
     * @throws CompressorException 压缩过程异常
     */
    byte[] compress(byte[] bytes);

    /**
     * 解压
     *
     * @param bytes 解压对象
     * @return 解压结果
     * @throws CompressorException 解压过程异常
     */
    byte[] decompress(byte[] bytes);

}
