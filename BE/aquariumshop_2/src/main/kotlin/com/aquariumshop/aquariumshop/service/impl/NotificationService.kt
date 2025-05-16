package com.aquariumshop.aquariumshop.service.impl

import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.v1.FirestoreClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService(
    private val firestore: Firestore
) {
    fun createNotificationToFirestore(userId: String, title: String, orderId: String, status: String) {
        val notification = mapOf(
            "title" to title,
            "orderId" to orderId,
            "status" to status,
            "timestamp" to FieldValue.serverTimestamp()
        )

        val future = firestore.collection("notifications")
            .document(userId)
            .collection("user_notifications")
            .add(notification)

        // Đợi hoàn thành để biết kết quả hoặc lỗi
        try {
            val docRef = future.get()  // Đây là blocking call
            println("Notification added with ID: ${docRef.id}")
        } catch (e: Exception) {
            println("Error adding notification: ${e.message}")
        }
    }
}
