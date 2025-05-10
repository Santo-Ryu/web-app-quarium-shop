package com.aquariumshop.aquariumshop.service

interface EncryptService {
    fun encrypt(data: String): String
    fun decrypt(encodedData: String): String
    fun hashPassword(password: String): String
}