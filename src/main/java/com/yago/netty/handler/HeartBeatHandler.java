package com.yago.netty.handler;

import java.util.UUID;

import com.yago.netty.protocol.protobuf.MessageBase.Message;
import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 17:15
 */

@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

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
}
