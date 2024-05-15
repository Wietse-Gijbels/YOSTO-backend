package com.yosto.yostobackend.chat;

import java.util.Date;

public class ChatMessageBuilder {

        String chatId;
        String senderId;
        String recipientId;
        String content;
        Date timestamp;

        public ChatMessageBuilder() {
        }

        public static ChatMessageBuilder chatMessageBuilder() {
            return new ChatMessageBuilder();
        }

        public ChatMessageBuilder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public ChatMessageBuilder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public ChatMessageBuilder recipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public ChatMessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ChatMessageBuilder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(this);
        }
}
