package com.lx.rpc.registry.zookeeper;

import com.lx.rpc.registry.ServiceRegistry;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description: 基于 ZooKeeper 的服务注册接口实现
 * @Author: LinXin_
 * @CreateTime: 2021/10/24 16:31
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry {


    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private final ZkClient zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        //创建ZooKeeper客户端
        zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        //创建 registry 节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        if(!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.debug("create registry node: {}",registryPath);
        }
        //创建 service节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if(!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}",servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address";
        String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.debug("create address node: {}", addressNode);
    }
}
