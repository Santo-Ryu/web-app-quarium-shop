package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class MessageController {
    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    fun send(message: ChatMessage): ChatMessage {
        return message
    }
}