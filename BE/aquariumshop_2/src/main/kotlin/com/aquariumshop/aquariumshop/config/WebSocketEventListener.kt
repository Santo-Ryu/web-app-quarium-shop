//package com.aquariumshop.aquariumshop.config
//
//import org.slf4j.LoggerFactory
//import org.springframework.context.event.EventListener
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor
//import org.springframework.stereotype.Component
//import org.springframework.web.socket.messaging.SessionConnectEvent
//import org.springframework.web.socket.messaging.SessionConnectedEvent
//import org.springframework.web.socket.messaging.SessionDisconnectEvent
//
//
//@Component
//class WebSocketEventListener {
//
//    private val logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)
//
//    @EventListener
//    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
//        val accessor = StompHeaderAccessor.wrap(event.message)
//        logger.info("New WebSocket connection attempt. SessionId: ${accessor.sessionId}")
//    }
//
//    @EventListener
//    fun handleWebSocketConnectedListener(event: SessionConnectedEvent) {
//        val accessor = StompHeaderAccessor.wrap(event.message)
//        logger.info("WebSocket connection established. SessionId: ${accessor.sessionId}")
//    }
//
//    @EventListener
//    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
//        val accessor = StompHeaderAccessor.wrap(event.message)
//        logger.info("WebSocket disconnected. SessionId: ${accessor.sessionId}")
//    }
//}
