package com.lx.rpc.common.bean;

/**
 * @Description: 封装 RPC 请求
 * @Author: LinXin_
 * @CreateTime: 2021/10/25 14:09
 */
public class RpcRequest {

    /**
     *  请求编号
     */
    private String requestId;
    /**
     *  请求接口名称
     */
    private String interfaceName;
    /**
     *  服务版本号
     */
    private String serviceVersion;
    /**
     *  服务方法名
     */
    private String methodName;
    /**
     *  方法参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     *  方法参数
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
