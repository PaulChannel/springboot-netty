package com.yago.netty.pojo;

import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 17:30
 */

public class HeartbeatResponsePacket extends Packet{

  @Override
  Byte getCommand() {
    return CommandType.HEARTBEAT_RESPONSE_VALUE;
  }
}
