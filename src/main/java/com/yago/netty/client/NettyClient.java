package com.yago.netty.client;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.yago.netty.handler.ClientHandlerInitializer;
import com.yago.netty.protocol.protobuf.MessageBase;
import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 16:26
 */

@Component
@Slf4j
public class NettyClient {

  private EventLoopGroup group = new NioEventLoopGroup();

  public SocketChannel socketChannel;
  @Value("${netty.port}")
  private Integer port;


  @Value("${netty.addr}")
  private String addr;

  @PostConstruct
  public void start() {
    Bootstrap bootStrap = new Bootstrap();

    bootStrap.group(group)
        .channel(NioSocketChannel.class)
        .remoteAddress(new InetSocketAddress(addr, port))
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.TCP_NODELAY, true) // 是否存在沾包拆包问题？
        .handler(new ClientHandlerInitializer());

    ChannelFuture future = bootStrap.connect();
    future.addListener((ChannelFutureListener) f -> {
      if (f.isSuccess()) {
        log.info("client start success");
      } else {
        log.warn("client start failed");
        // 进行断线重连
        f.channel().eventLoop().schedule(() -> {
          start();
        }, 20, TimeUnit.SECONDS);
      }
    });

    socketChannel = (SocketChannel) future.channel();


  }

  @PreDestroy
  public void destroy() throws InterruptedException {
    group.shutdownGracefully().sync();
    socketChannel.close();
  }

  public void sendMsg(String msg) {
    MessageBase.Message message = MessageBase.Message.newBuilder().setCmd(CommandType.NORMAL).setContent(msg).setRequestId(UUID.randomUUID().toString()).build();

    socketChannel.writeAndFlush(message);
  }

}
