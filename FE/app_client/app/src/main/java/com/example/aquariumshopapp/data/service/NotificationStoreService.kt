package com.example.aquariumshopapp.data.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.aquariumshopapp.data.model.NotificationItem
import kotlinx.coroutines.tasks.await

class NotificationStoreService {
    companion object {
        private val db = FirebaseFirestore.getInstance()

        /**
         * Lấy danh sách notifications của user
         * @param userId ID của user
         * @return List<NotificationItem>
         */
        suspend fun getNotifications(userId: String): List<NotificationItem> {
            return try {
                val snapshot = db.collection("notifications")
                    .document(userId)
                    .collection("user_notifications")
                    .orderBy("timestamp") // có thể order theo timestamp nếu muốn
                    .get()
                    .await()

                snapshot.documents.mapNotNull { doc ->
                    try {
                        NotificationItem(
                            userId = doc.id,
                            orderId = doc.getString("orderId").toString(),
                            title = doc.getString("title") ?: "",
                            timestamp = doc.getTimestamp("timestamp")?.toDate(),
                            status = doc.getString("status").toString()
                        )
                    } catch (e: Exception) {
                        null // bỏ qua nếu lỗi
                    }
                }
            } catch (e: Exception) {
                Log.e("NotificationStoreService", "Lỗi lấy notifications: $e")
                emptyList()
            }
        }
    }
}
