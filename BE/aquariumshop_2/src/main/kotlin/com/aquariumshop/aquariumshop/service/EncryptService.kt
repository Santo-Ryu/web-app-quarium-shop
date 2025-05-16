package com.aquariumshop.aquariumshop.service

interface EncryptService {
    fun encrypt(plainText: String): String
    fun decrypt(encryptedText: String): String
    fun hashPassword(password: String): String
}