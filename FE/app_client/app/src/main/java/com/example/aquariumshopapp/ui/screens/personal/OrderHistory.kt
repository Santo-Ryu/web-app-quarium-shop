package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import com.example.aquariumshopapp.ui.screens.components.LabelList
import com.example.aquariumshopapp.ui.screens.personal.components.OrderItem

@Composable
fun OrderHistory(navController: NavController) {
    val labelList = listOf<String>("Tất cả", "Hoàn thành", "Đang giao", "Đang chờ xử lý", "Đã hủy")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Lịch sử", "personal")
        Spacer(modifier = Modifier.height(12.dp))
        LabelList(labelList, navController)

        Column(
            modifier = Modifier.weight(1f).padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OrderItem(navController)
        }
    }
}