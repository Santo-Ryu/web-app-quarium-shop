package com.example.aquariumshopapp.ui.screens.notification

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.model.NotificationItem
import com.example.aquariumshopapp.data.service.NotificationStoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationsViewModel: ViewModel() {
    private val _notificationList = MutableStateFlow<List<NotificationItem>>(emptyList())

    val notifications: StateFlow<List<NotificationItem>> = _notificationList

    suspend fun getNotifications(context: Context) {
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id.toString()
        _notificationList.value = NotificationStoreService.getNotifications(id)
    }
}