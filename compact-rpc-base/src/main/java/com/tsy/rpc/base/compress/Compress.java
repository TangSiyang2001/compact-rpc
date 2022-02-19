package com.tsy.rpc.base.compress;

import com.tsy.rpc.base.extension.annotation.SPI;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@SPI
public interface Compress {

    /**
     * 压缩
     * @param bytes 压缩对象
     * @return 压缩结果
     */
    byte[] compress(byte[] bytes);

    /**
     * 解压
     * @param bytes 解压对象
     * @return 解压结果
     */
    byte[] decompress(byte[] bytes);

}
