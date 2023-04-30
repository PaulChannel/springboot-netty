package com.yago.netty.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/18 14:40
 */


public class NettyIdleServerHandler  extends IdleStateHandler {

  private static final Integer IDLE_TIME = 30;


  public NettyIdleServerHandler() {
    super(IDLE_TIME, 0, 0, TimeUnit.SECONDS);
  }


  @Override
  protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
    super.channelIdle(ctx, evt);
    ctx.channel().close();
  }
}
