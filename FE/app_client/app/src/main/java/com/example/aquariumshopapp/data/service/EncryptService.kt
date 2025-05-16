package com.example.aquariumshopapp.data.service

import java.security.MessageDigest
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class EncryptService {
    companion object {
        private val secureRandom = SecureRandom()

        private fun generateAESKey(): ByteArray {
            val key = ByteArray(32)
            secureRandom.nextBytes(key)
            return key
        }

        private fun getIVCode(): ByteArray {
            val iv = ByteArray(16)
            secureRandom.nextBytes(iv)
            return iv
        }

        private fun timeStamp(): String {
            val now = LocalDateTime.now()
            return String.format("%02d%02d%02d", now.hour, now.minute, now.second)
        }

        private data class TransformedKey(
            val newAESKey: SecretKey,
            val iv: ByteArray,
            val timeStr: String
        )

        private fun transformKey(aesKey: ByteArray, iv: ByteArray, timeStr: String): TransformedKey {
            val timeShift = timeStr.substring(0, 2).toInt() *
                    timeStr.substring(2, 4).toInt() *
                    timeStr.substring(4, 6).toInt()

            val iterations = Math.max(1000, Math.min(10000, timeStr.toInt() * 10))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec = PBEKeySpec(
                Base64.getEncoder().encodeToString(aesKey).toCharArray(),
                iv,
                iterations,
                256
            )

            val newAESKey = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")

            return TransformedKey(newAESKey, iv, timeStr)
        }

        private fun mixEncodedData(aesKey: ByteArray, encryptData: ByteArray, iv: ByteArray, timeStr: String): String {
            val aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey)
            val ivBase64 = Base64.getEncoder().encodeToString(iv)
            val timeStrBase64 = Base64.getEncoder().encodeToString(timeStr.toByteArray())
            val encryptDataBase64 = Base64.getEncoder().encodeToString(encryptData)

            val aesKeyParts = Array(4) { i -> aesKeyBase64.substring(i * 11, (i + 1) * 11) }
            val ivParts = Array(4) { i -> ivBase64.substring(i * 6, (i + 1) * 6) }

            val mixedParts = StringBuilder()
            for (i in 0 until 4) {
                mixedParts.append(aesKeyParts[i]).append(ivParts[i])
            }

            return encryptDataBase64.substring(0, 8) +
                    mixedParts.substring(0, 32) +
                    timeStrBase64 +
                    mixedParts.substring(32) +
                    encryptDataBase64.substring(8)
        }

        fun encrypt(data: String): String {
            val aesKey = generateAESKey()
            val ivCode = getIVCode()
            val timeStr = timeStamp()

            val transformed = transformKey(aesKey, ivCode, timeStr)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, transformed.newAESKey, IvParameterSpec(transformed.iv))

            val encryptedData = cipher.doFinal(data.toByteArray())

            return mixEncodedData(aesKey, encryptedData, transformed.iv, transformed.timeStr)
        }

        fun decrypt(encodedData: String): String {
            val encryptDataBase64 = encodedData.substring(0, 8) + encodedData.substring(84)
            val mixedPartsBase64 = encodedData.substring(8, 40) + encodedData.substring(48, 84)
            val timeStrBase64 = encodedData.substring(40, 48)

            val timeStr = String(Base64.getDecoder().decode(timeStrBase64))

            val parsed = parseMixedParts(mixedPartsBase64)
            val transformed = transformKey(parsed.aesKey, parsed.ivCode, timeStr)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, transformed.newAESKey, IvParameterSpec(transformed.iv))

            val decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptDataBase64))

            return String(decryptedData)
        }

        private data class ParsedParts(val aesKey: ByteArray, val ivCode: ByteArray)

        private fun parseMixedParts(mixedParts: String): ParsedParts {
            val aesKeyParts = Array(4) { i -> mixedParts.substring(i * 17, i * 17 + 11) }
            val ivParts = Array(4) { i -> mixedParts.substring(i * 17 + 11, (i + 1) * 17) }

            val aesKey = aesKeyParts.joinToString("")
            val ivCode = ivParts.joinToString("")

            return ParsedParts(
                Base64.getDecoder().decode(aesKey),
                Base64.getDecoder().decode(ivCode)
            )
        }

        fun hashPassword(password: String): String {
            return try {
                val digest = java.security.MessageDigest.getInstance("SHA-256")
                val hash = digest.digest(password.toByteArray())
                hash.joinToString("") { "%02x".format(it) }
            } catch (e: Exception) {
                throw RuntimeException("Error hashing password", e)
            }
        }

//        private val key = "0123456789abcdef0123456789abcdef".toByteArray(Charsets.UTF_8) // 32 bytes
//        private val iv = "abcdef9876543210".toByteArray(Charsets.UTF_8) // 16 bytes
//
//        fun encrypt(plainText: String): String {
//            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//            val secretKey = SecretKeySpec(key, "AES")
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
//            val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
//            return Base64.getEncoder().encodeToString(encryptedBytes)
//        }
//
//        fun decrypt(encryptedText: String): String {
//            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//            val secretKey = SecretKeySpec(key, "AES")
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
//            val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText))
//            return String(decryptedBytes, Charsets.UTF_8)
//        }
    }
}