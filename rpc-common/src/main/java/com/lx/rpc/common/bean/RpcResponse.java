package com.lx.rpc.common.bean;


/**
 * @Description: 封装 RPC 响应
 * @Author: LinXin_
 * @CreateTime: 2021/10/25 14:16
 */
public class RpcResponse {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 对应异常信息
     */
    private Exception exception;

    /**
     * 结果集
     */
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
