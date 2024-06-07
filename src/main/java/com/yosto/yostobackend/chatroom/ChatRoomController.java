package com.yosto.yostobackend.chatroom;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/getChatRoom/{chatId}")
    public ChatRoom getChat(@PathVariable String chatId) {
        return chatRoomService.getChat(chatId);
    }

    @GetMapping("/getMyChatRooms/{id}")
    public List<ChatRoom> getMyChatRooms(@PathVariable UUID id) {
        return chatRoomService.getMyChatRooms(id);
    }

    @PostMapping("/sluitChat")
    public void sluitChat(@RequestBody Map<String, String> request) {
        String chatId = request.get("chatId");
        chatRoomService.sluitChat(chatId);
    }

}
