package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.enums.EmailPurpose
import com.aquariumshop.aquariumshop.enums.Role
import com.aquariumshop.aquariumshop.service.EmailService
import com.aquariumshop.aquariumshop.service.EncryptService
import lombok.RequiredArgsConstructor
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Value
import java.security.SecureRandom
import java.util.Base64

@Service
@RequiredArgsConstructor
class EmailServiceImpl(
    private val mailSender: JavaMailSender,
    val encryptServiceImpl: EncryptServiceImpl
) : EmailService {
    @Value("\${spring.mail.username}")
    private lateinit var emailFrom: String

    override fun sendEmail(name: String, token: String, email: String, purpose: EmailPurpose, role: Role) {
        try {
            val password = generateNewPassword()

            val file = when (purpose) {
                EmailPurpose.ACCOUNT_VERIFICATION -> ResourceUtils.getFile("src/main/resources/templates/email_text.html")
                EmailPurpose.PASSWORD_RESET -> ResourceUtils.getFile("src/main/resources/templates/password_reset.html")
            }
            var body = Files.readString(Paths.get(file.toURI()))

            val subject = when (purpose) {
                EmailPurpose.ACCOUNT_VERIFICATION -> "Aquarium Shop - Xác thực tài khoản"
                EmailPurpose.PASSWORD_RESET -> "Aquarium Shop - Lấy lại mật khẩu"
            }

            body = body.replace("{{name}}", name)
                .replace("{{token}}", token)
                .replace("{{email}}", email)
                .replace("{{purpose}}", purpose.toString())
                .replace("{{role}}", role.toString())

            if (purpose === EmailPurpose.PASSWORD_RESET) {
                body = body.replace("{{password}}", password)
            }

            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(emailFrom)
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(body, true)

            mailSender.send(message)

            println("SEND MAIL SUCCESSFUL: $purpose")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun generateToken(): String {
        val secureRandom = SecureRandom()
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }

    override fun generateNewPassword(): String {
        val secureRandom = SecureRandom()
        val number = secureRandom.nextInt(900000) + 100000 // tạo số từ 100000 đến 999999
        return number.toString()
    }
}