package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.enums.Role
import com.aquariumshop.aquariumshop.model.entity.Admin
import com.aquariumshop.aquariumshop.model.entity.Customer
import com.aquariumshop.aquariumshop.service.JWTService
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.HexFormat
import java.util.UUID

@Service
@RequiredArgsConstructor
class JWTServiceImpl: JWTService {
    @Value("\${jwt.secret}")
    private lateinit var SECRET_KEY: String

    @Value("\${jwt.expiration}")
    private lateinit var JWT_EXPIRATION: String

    override fun getSignInKey(): ByteArray {
        val bytes = hexStringToByteArray(SECRET_KEY)
        return bytes
    }

    override fun generateAdminToken(admin: Admin): String {
        val now = Date()
        val expiration = Date(now.time + JWT_EXPIRATION.toLong()) // 24h

        val claims = JWTClaimsSet.Builder()
            .subject(admin.email)
            .expirationTime(expiration)
            .claim("userId", admin.id)
            .claim("role", Role.ADMIN)
            .claim("jti", UUID.randomUUID().toString())
            .build()

        val signer = MACSigner(getSignInKey())
        val signedJWT = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claims)
        signedJWT.sign(signer)

        return signedJWT.serialize()
    }

    override fun generateCustomerToken(customer: Customer): String {
        val now = Date()
        val expiration = Date(now.time + JWT_EXPIRATION.toLong()) // 24h

        val claims = JWTClaimsSet.Builder()
            .subject(customer.email)
            .expirationTime(expiration)
            .claim("userId", customer.id)
            .claim("role", Role.CUSTOMER)
            .claim("jti", UUID.randomUUID().toString())
            .build()

        val signer = MACSigner(getSignInKey())
        val signedJWT = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claims)
        signedJWT.sign(signer)

        return signedJWT.serialize()
    }

    override fun hexStringToByteArray(s: String): ByteArray {
        return HexFormat.of().parseHex(s) // Java 17+
    }

    override fun extractEmail(token: String): String {
        return extractClaim(token) { it.subject }
    }

    override fun <T> extractClaim(token: String, claimsResolver: (JWTClaimsSet) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    override fun extractAllClaims(token: String): JWTClaimsSet {
        val signedJWT = SignedJWT.parse(token)
        val verifier = MACVerifier(getSignInKey())
        if (!signedJWT.verify(verifier)) {
            throw IllegalArgumentException("Invalid JWT signature")
        }
        return signedJWT.jwtClaimsSet
    }

    override fun isAdminTokenValid(token: String, admin: Admin): Boolean {
        val email = extractEmail(token)
        return email == admin.email && !isTokenExpired(token)
    }

    override fun isCustomerTokenValid(token: String, customer: Customer): Boolean {
        val email = extractEmail(token)
        return email == customer.email && !isTokenExpired(token)
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return email == userDetails.username && !isTokenExpired(token)
    }

    override fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).isBefore(LocalDateTime.now())
    }

    override fun extractExpiration(token: String): LocalDateTime {
        val expirationDate: Date = extractClaim(token) { it.expirationTime }
        return expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    override fun extractJti(token: String): String {
        return extractClaim(token) { it.getStringClaim("jti") }
    }
}