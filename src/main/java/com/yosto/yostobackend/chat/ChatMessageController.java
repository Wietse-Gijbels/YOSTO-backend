package com.yosto.yostobackend.chat;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatMessageController {
  private final ChatMessageService chatMessageService;

  public ChatMessageController(ChatMessageService chatMessageService) {
    this.chatMessageService = chatMessageService;
  }

  @MessageMapping("/chat")
  public void processMessage(@Payload ChatMessage chatMessage) {
    chatMessageService.processMessage(chatMessage);
  }

  @GetMapping("/messages/{chatId}")
  public ResponseEntity<List<ChatMessage>> findChatMessages(
          @PathVariable String chatId
  ) {
    return ResponseEntity.ok(chatMessageService.findChatMessages(chatId));
  }
}
