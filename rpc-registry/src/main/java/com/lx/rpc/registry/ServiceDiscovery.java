package com.lx.rpc.registry;

/**
 * @Description: 服务发现接口
 * @Author: LinXin_
 * @CreateTime: 2021/10/24 15:46
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务地址
     *
     * @param serviceName 服务名称
     * @return 服务地址
     */
    String discover(String serviceName);
}
