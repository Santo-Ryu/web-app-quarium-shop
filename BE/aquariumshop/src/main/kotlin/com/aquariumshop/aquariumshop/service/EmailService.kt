package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.enums.EmailPurpose
import com.aquariumshop.aquariumshop.service.support.Recipient

interface EmailService {
    fun sendEmail(name: String, token: String, email: String, purpose: EmailPurpose, recipient: Recipient): Unit
}