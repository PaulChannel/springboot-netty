package com.yago.netty.handler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.yago.netty.client.NettyClient;
import com.yago.netty.protocol.protobuf.MessageBase.Message;
import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 17:15
 */

@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

  @Autowired
  NettyClient nettyClient;
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if(evt instanceof IdleStateEvent) {
      log.info("客户端已经10s未发送消息");

      Message heartBeat = new Message().toBuilder().setCmd(CommandType.HEARTBEAT_REQUEST)
          .setContent("heartBeat")
          .setRequestId(UUID.randomUUID().toString())
          .build();

      ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

  // 服务端挂掉了进行重连
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    EventLoop eventLoop = ctx.channel().eventLoop();
    log.warn("客户端进行重连尝试");
    eventLoop.schedule(() -> {
      nettyClient.start();
    }, 10L, TimeUnit.SECONDS);
    super.channelInactive(ctx);
  }
}
