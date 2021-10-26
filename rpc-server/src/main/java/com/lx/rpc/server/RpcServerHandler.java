package com.lx.rpc.server;

import com.lx.rpc.common.bean.RpcRequest;
import com.lx.rpc.common.bean.RpcResponse;
import com.lx.rpc.common.util.StringUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description: RPC服务端处理器（用于处理RPC请求）
 * @Author: LinXin_
 * @CreateTime: 2021/10/26 13:28
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        // 创建并初始化 RPC 响应对象
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handler(request);
            response.setResult(result);
        } catch (Exception e) {
            LOGGER.error("handler result failure",e);
            response.setException(e);
        }
        // 写入 RPC 响应对象 并设置监听器 自动关闭连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handler(RpcRequest request) throws InvocationTargetException {
        // 获取服务请求对象
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();

        if (StringUtil.isNotEmpty(serviceVersion)) {
            serviceName += "-" + serviceVersion;
        }
        Object serviceBean = handlerMap.get(serviceName);
        if (serviceBean == null) {
            return new RuntimeException(String.format("can not find service bean by key %s", serviceName));
        }
        // 获取反射调用所需参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // 使用 CGlib 执行反射调用
        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod fastClassMethod = fastClass.getMethod(methodName, parameterTypes);
        return fastClassMethod.invoke(serviceBean,parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}
