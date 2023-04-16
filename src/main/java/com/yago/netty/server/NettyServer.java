package com.yago.netty.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.yago.netty.handler.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yago.hu
 * @Description netty服务端
 * @createTime 2023年04月16日 22:54:00
 */
@Component
@Slf4j
public class NettyServer {

	@Value("${netty.port}")
	private Integer port;
	/**
	 * 管理连接的group
	 */
	EventLoopGroup boss = new NioEventLoopGroup();

	/**
	 * 用于数据处理的group
	 */
	EventLoopGroup work = new NioEventLoopGroup();

	/**
	 * 启动服务端方法
	 */
	@PostConstruct
	public void start() throws InterruptedException {
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		serverBootstrap.group(boss)
				.channel(NioServerSocketChannel.class)
				.localAddress(port)
				.option(ChannelOption.SO_BACKLOG, 1024)
				// 设置tcp长链接
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				// 设置tcp延时传输，提高网络负载
				.childOption(ChannelOption.TCP_NODELAY, true)
				// 添加Handler
				.childHandler(new NettyServerInitializer());
		serverBootstrap.bind().sync();

		log.info("netty server started!");

	}

	/**
	 * 服务端关闭前资源释放
	 */
	@PreDestroy
	public void destroy() throws InterruptedException {
		boss.shutdownGracefully().sync();
		work.shutdownGracefully().sync();
		log.info("netty server shutdown");
	}


}
