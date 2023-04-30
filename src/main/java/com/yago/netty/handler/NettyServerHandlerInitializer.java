package com.yago.netty.handler;

import com.yago.netty.protocol.protobuf.MessageBase;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 17:20
 */

public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

  @Override
  protected void initChannel(Channel ch) throws Exception {
    // 添加空闲检测处理器
    ch.pipeline().addLast(new IdleStateHandler(0, 10 ,0))
        // 解码器
        .addLast(new ProtobufVarint32FrameDecoder())
        .addLast(new ProtobufDecoder(MessageBase.Message.getDefaultInstance()))
        .addLast(new ProtobufVarint32LengthFieldPrepender())
        .addLast(new ProtobufEncoder())
        .addLast(new NettyIdleServerHandler())
        .addLast(new NettyServerHandler());
  }
}
