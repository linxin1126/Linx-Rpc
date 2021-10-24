package com.lx.rpc.registry;

/**
 * @Description: 服务注册接口
 * @Author: LinXin_
 * @CreateTime: 2021/10/24 15:39
 */
public interface ServiceRegistry {

    /**
     * 注册服务名称与服务地址
     *
     * @param serviceName
     * @param serviceAddress
     */
    void register(String serviceName, String serviceAddress);
}
