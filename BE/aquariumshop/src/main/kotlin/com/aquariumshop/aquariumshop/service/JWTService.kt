package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.model.entity.Admin
import com.aquariumshop.aquariumshop.model.entity.Customer
import com.nimbusds.jwt.JWTClaimsSet
import java.time.LocalDateTime

interface JWTService {
    fun getSignInKey(): ByteArray

    fun generateCustomerToken(customer: Customer): String

    fun generateAdminToken(admin: Admin): String

    fun extractEmail(token: String): String

    fun <T> extractClaim(token: String, claimsResolver: (JWTClaimsSet) -> T): T

    fun extractAllClaims(token: String): JWTClaimsSet

    fun isCustomerTokenValid(token: String, customer: Customer): Boolean

    fun isAdminTokenValid(token: String, admin: Admin): Boolean

    fun hexStringToByteArray(s: String): ByteArray

    fun isTokenExpired(token: String): Boolean

    fun extractExpiration(token: String): LocalDateTime

    fun extractJti(token: String): String
}