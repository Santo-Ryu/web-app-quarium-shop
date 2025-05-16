//package com.aquariumshop.aquariumshop.config
//
//import com.nimbusds.jose.JWSObject
//import com.nimbusds.jose.crypto.MACVerifier
//import org.springframework.http.server.ServerHttpRequest
//import org.springframework.http.server.ServerHttpResponse
//import org.springframework.web.socket.server.HandshakeInterceptor
//import java.security.Principal
//
//
//class AuthHandshakeInterceptor(
//    private val jwtSecret: String
//) : HandshakeInterceptor {
//
//    override fun beforeHandshake(
//        request: ServerHttpRequest,
//        response: ServerHttpResponse,
//        wsHandler: org.springframework.web.socket.WebSocketHandler,
//        attributes: MutableMap<String, Any>
//    ): Boolean {
//        val authHeader = request.headers.getFirst("Authorization") ?: return false
//        if (!authHeader.startsWith("Bearer ")) return false
//
//        val token = authHeader.removePrefix("Bearer ").trim()
//
//        try {
//            val jwsObject = JWSObject.parse(token)
//            val verifier = MACVerifier(jwtSecret.toByteArray())
//
//            if (!jwsObject.verify(verifier)) return false
//
//            val payload = jwsObject.payload.toJSONObject()
//            val username = payload["sub"] as? String ?: return false
//
//            attributes["user"] = UsernamePrincipal(username)
//            return true
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//    }
//
//    override fun afterHandshake(
//        request: ServerHttpRequest,
//        response: ServerHttpResponse,
//        wsHandler: org.springframework.web.socket.WebSocketHandler,
//        exception: Exception?
//    ) {
//        // No-op
//    }
//
//    class UsernamePrincipal(private val username: String) : Principal {
//        override fun getName(): String = username
//    }
//}
