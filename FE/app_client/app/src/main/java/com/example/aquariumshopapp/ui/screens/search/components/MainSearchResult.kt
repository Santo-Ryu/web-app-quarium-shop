package com.example.aquariumshopapp.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.model.TestData
import com.example.aquariumshopapp.ui.screens.components.LabelList
import com.example.aquariumshopapp.ui.screens.components.ProductCardList
import com.example.aquariumshopapp.ui.theme.productCardModifier

@Composable
fun MainSearchResult(modifier: Modifier, navController: NavController) {
    val labelList = listOf<String>(
        "Tất cả", "Liên quan", "Mới nhất", "Bán chạy", "Giá giảm dần", "Giá tăng dần"
    )
    val dataList by remember{
        mutableStateOf(
            listOf(
                TestData(R.drawable.cay_dong_tien, "12,000", "Cây đồng tiền", "Sống được trên cạn lẫn dưới nước"),
                TestData(R.drawable.beta2, "120,000", "Cá beta Rồng", "Siêu đẹp, mạnh mẽ như loài rồng"),
                TestData(R.drawable.beca, "89,000", "Bể cá kinh xanh", "Trong suốt, đẹp đẽ, sang trọng, lịch lãm"),
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .then(modifier)
            .fillMaxSize()
    ) {
        LabelList(labelList, navController)

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            ProductCardList(
                dataList,
                Modifier
                    .productCardModifier()
                    .then(Modifier.weight(1f)),
                navController
            )
        }
    }
}