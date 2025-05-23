package com.aquariumshop.aquariumshop.dto

import java.time.LocalDateTime

data class ChatMessage(
    val sender: String,
    val content: String,
    val type: String, // message | notification
    val timestamp: String = LocalDateTime.now().toString()
)