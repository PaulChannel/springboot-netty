package com.yago.netty.pojo;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/17 17:31
 */

public abstract class Packet {

  String version = "1";

  abstract Byte getCommand();

}
