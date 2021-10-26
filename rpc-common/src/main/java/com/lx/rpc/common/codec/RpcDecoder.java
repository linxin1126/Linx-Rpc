package com.lx.rpc.common.codec;

import com.lx.rpc.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description: Rpc 解码器
 * @Author: LinXin_
 * @CreateTime: 2021/10/25 14:34
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int maxRead = 4;
        //获取消息头所标识的消息体字节数组长度
        if (in.readableBytes() < maxRead) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        //若当前可以获取到的字节数小于实际长度，则直接返回，直到当前可以获取到的字节数等于实际长度
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        //读取完整的消息体字节数组
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        //将字节数组反序列化为java对象
        out.add(SerializationUtil.deserialize(data,genericClass));
    }
}
