import com.lx.rpc.common.bean.RpcRequest;
import com.lx.rpc.common.bean.RpcResponse;
import com.lx.rpc.common.codec.RpcDecoder;
import com.lx.rpc.common.codec.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: Rpc 客户端（用于发送Rpc请求）
 * @Author: LinXin_
 * @CreateTime: 2021/10/27 09:34
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);

    private String host;
    private int port;
    private RpcResponse response;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        this.response = response;
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("apo caught exception",cause);
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建 Netty 客户端 并初始化
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline()
                            // 将 RPC 请求进行编码（为了发送请求）
                            .addLast(new RpcEncoder(RpcRequest.class))
                            // 将 RPC 响应进行解码（为了处理响应）
                            .addLast(new RpcDecoder(RpcResponse.class))
                            // 处理 RPC 响应（使用RpcClient 发送 RPC 请求）
                            .addLast(RpcClient.this);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 连接 RPC 服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            // 返回 RPC 响应对象
            return response;
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

}
