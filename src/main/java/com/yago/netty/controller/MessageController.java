package com.yago.netty.controller;

import com.yago.netty.client.NettyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yougen.hu
 * @time: 2023/4/18 15:02
 */


@RestController
@RequiredArgsConstructor
public class MessageController {

  private final NettyClient nettyClient;

  @GetMapping("/sendMessage")
  public Object sendMessage(String msg) {
    nettyClient.sendMsg(msg);
    return "success";
  }
}
