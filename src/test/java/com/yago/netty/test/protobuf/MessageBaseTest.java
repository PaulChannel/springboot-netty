package com.yago.netty.test.protobuf;

import java.util.UUID;

import com.yago.netty.protocol.protobuf.MessageBase.Message;
import com.yago.netty.protocol.protobuf.MessageBase.Message.CommandType;
import org.junit.Test;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/18 15:40
 */

public class MessageBaseTest {

  @Test
  public void testMessageBase() {
    Message hi = Message.newBuilder().setRequestId(UUID.randomUUID().toString()).setContent("hi").setCmd(CommandType.NORMAL).build();

    System.out.println(hi.toString());
  }
}
