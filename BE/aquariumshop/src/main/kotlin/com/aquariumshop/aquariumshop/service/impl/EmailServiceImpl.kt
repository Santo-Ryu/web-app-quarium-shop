package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.enums.EmailPurpose
import com.aquariumshop.aquariumshop.service.EmailService
import com.aquariumshop.aquariumshop.service.support.Recipient
import lombok.RequiredArgsConstructor
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Value

@Service
@RequiredArgsConstructor
class EmailServiceImpl(
    private val mailSender: JavaMailSender
) : EmailService {
    @Value("\${spring.mail.username}")
    private lateinit var emailFrom: String

    override fun sendEmail(name: String, token: String, email: String, purpose: EmailPurpose, recipient: Recipient) {
        try {
            val file = ResourceUtils.getFile("src/main/resources/templates/email_text.html")
            var body = Files.readString(Paths.get(file.toURI()))

            val role = when (recipient) {
                is Recipient.Admin -> "Admin"
                is Recipient.Customer -> "Customer"
            }

            val subject = when (purpose) {
                EmailPurpose.ACCOUNT_VERIFICATION -> "Aquarium Shop - Xác thực tài khoản"
                EmailPurpose.PASSWORD_RESET -> "Aquarium Shop - Lấy lại mật khẩu"
            }

            body = body.replace("{{name}}", name) // tên người nhận
                .replace("{{token}}", token) // token
                .replace("{{email}}", email) // email
                .replace("{{purpose}}", purpose.toString()) // mục đích
                .replace("{{role}}", role)

            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(emailFrom)
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(body, true)

            mailSender.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}