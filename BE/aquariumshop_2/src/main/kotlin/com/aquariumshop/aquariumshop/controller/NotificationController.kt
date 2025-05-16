package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.service.impl.NotificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {

    @GetMapping("/notify")
    fun notifyUser(
        @RequestParam userId: String,
        @RequestParam message: String
    ): Any {

        return ""
    }
}
