package com.tsy.rpc.compress;

import com.tsy.rpc.base.compress.Compressor;
import com.tsy.rpc.base.exception.CompressorException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Steven.T
 * @date 2022/3/15
 */
public class GzipCompressor implements Compressor {
    private static final int BUFFER_SIZE = 1024 * 4 * 4;

    @Override
    public byte[] compress(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Bytes should be not null.");
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gos = new GZIPOutputStream(bos)) {
            gos.write(bytes);
            gos.flush();
            gos.finish();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new CompressorException("Compress failed.", e);
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Bytes should be not null.");
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = (gis.read(buffer))) != -1) {
                bos.write(buffer, 0, n);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new CompressorException("Decompress failed.", e);
        }
    }
}
