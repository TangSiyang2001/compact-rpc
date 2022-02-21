package com.tsy.rpc.loadbalance;

import com.tsy.rpc.base.loadbalance.LoadBalancer;
import org.apache.commons.collections4.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Steven.T
 * @date 2022/2/21
 */
public abstract class AbstractBalancer implements LoadBalancer {

    @Override
    public InetSocketAddress selectService(List<InetSocketAddress> addresses) {
        if (CollectionUtils.isEmpty(addresses)) {
            return null;
        }
        if(addresses.size() == 1){
            return addresses.get(0);
        }
        return doSelect(addresses);
    }

    /**
     * 实际的选取逻辑
     * @param addresses 地址列表
     * @return 实际地址
     */
    protected abstract InetSocketAddress doSelect(List<InetSocketAddress> addresses);
}
