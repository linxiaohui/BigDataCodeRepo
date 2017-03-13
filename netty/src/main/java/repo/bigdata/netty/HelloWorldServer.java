package repo.bigdata.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

class HelloWorldHandler extends ChannelInboundHandlerAdapter {
	
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	Channel incoming = ctx.channel();
        System.out.println("[Remote-" + incoming.remoteAddress() + "] connected");
    }
    
    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
		System.out.println("[Remote-"+incoming.remoteAddress()+"] online");
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("Server received: " + buf.toString(CharsetUtil.UTF_8));
		ByteBuf outbuf = Unpooled.copiedBuffer("Hello World! " + buf.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8);
		/* .write() sends the buffer to the next ChannelHandler*/
		ctx.write(outbuf).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				if (future.isSuccess()) {
					System.out.println("Write successful");
				} else {
					System.err.println("Write error");
					future.cause().printStackTrace();
				}
			}
		});
		;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}

public class HelloWorldServer {

	public static void main(String[] args) throws InterruptedException {

		/* 创建 ServerBootstrap 实例 */
		ServerBootstrap bootstrap = new ServerBootstrap();
		/* 设置并绑定Reactor线程池 */
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		bootstrap.group(boss, worker);
		/* 设置并绑定服务端Channel */
		bootstrap.channel(NioServerSocketChannel.class);
		/* 添加设置ChannelHandler, add a single ChannelHandler*/
		//bootstrap.childHandler(new HelloWorldHandler());
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				System.out.println("Server Bootstrap");
				ch.pipeline().addLast(new HelloWorldHandler());
			}
		});

		// bootstrap.localAddress(new InetSocketAddress(12345));
		try {
			/* 绑定并启动监听端口 */
			ChannelFuture f = bootstrap.bind(12345).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully().sync();
			worker.shutdownGracefully().sync();
		}
	}

}
