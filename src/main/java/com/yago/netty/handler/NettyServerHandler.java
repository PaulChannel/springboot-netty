package com.yago.netty.handler;

import com.yago.netty.pojo.HeartbeatResponsePacket;
import com.yago.netty.protocol.protobuf.MessageBase;
import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yago.hu
 * @Description
 * @createTime 2023年04月16日 23:14:00
 */

@Component
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<MessageBase.Message> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageBase.Message msg) throws Exception {
    if (msg.getCmd().equals(CommandType.HEARTBEAT_REQUEST)) {
      log.info("收到来自客户端的心跳消息 : {}", msg.toString());
      // 回应pong
      channelHandlerContext.writeAndFlush(new HeartbeatResponsePacket());
    } else if (msg.getCmd().equals(CommandType.NORMAL)) {
      log.info("收到客户端普通业务消息{}", msg.toString());
    }
  }
}
