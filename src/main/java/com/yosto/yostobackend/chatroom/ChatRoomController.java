package com.yosto.yostobackend.chatroom;

import com.yosto.yostobackend.gebruiker.Gebruiker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/getMyChatRooms/{id}")
    public List<ChatRoom> getMyChatRooms(@PathVariable UUID id) {
        return chatRoomService.getMyChatRooms(id);
    }
}
