package com.tsy.rpc.loadbalance;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * @author Steven.T
 * @date 2022/2/21
 */
@Slf4j
public class RandomBalancer extends AbstractBalancer{

    @Override
    protected InetSocketAddress doSelect(List<InetSocketAddress> addresses) {
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
            final int index = random.nextInt(addresses.size());
            return addresses.get(index);
        } catch (NoSuchAlgorithmException e) {
            log.error("Random algorithm is not available.");
        }
        return null;
    }
}
