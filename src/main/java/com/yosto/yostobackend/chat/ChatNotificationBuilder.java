package com.yosto.yostobackend.chat;

import java.util.UUID;

public class ChatNotificationBuilder {

        UUID id;
        String senderId;
        String recipientId;
        String content;

        public ChatNotificationBuilder() {
        }

        public static ChatNotificationBuilder chatNotificationBuilder() {
            return new ChatNotificationBuilder();
        }

        public ChatNotificationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ChatNotificationBuilder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public ChatNotificationBuilder recipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;
        }

        public ChatNotificationBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ChatNotification build() {
            return new ChatNotification(this);
        }
}