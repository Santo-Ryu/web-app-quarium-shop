package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import com.example.aquariumshopapp.ui.screens.components.LabelList
import com.example.aquariumshopapp.ui.screens.personal.components.OrderItem
import com.example.aquariumshopapp.ui.screens.personal.PersonalViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OrderHistory(navController: NavController, viewModel: PersonalViewModel = viewModel()) {
    val context = LocalContext.current
    val categories = viewModel.categories.collectAsState().value
    val orders = viewModel.orders.collectAsState().value
    val orderItems = viewModel.orderItems.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Lịch sử", "personal")
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.weight(1f).padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            orders.sortedByDescending {
                LocalDateTime.parse(
                    it.createdAt,
                    DateTimeFormatter.ISO_DATE_TIME
                )
            }.forEach { item ->
                OrderItem(
                    id = item.id,
                    navController = navController,
                    orderCode = item.id,
                    status = item.status.statusName.toString()
                )
            }
        }
    }
}