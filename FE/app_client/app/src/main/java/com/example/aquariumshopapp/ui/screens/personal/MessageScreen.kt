package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.MESSAGE_SEND
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.personal.components.MessageItemAdmin
import com.example.aquariumshopapp.ui.screens.personal.components.MessageItemCustomer
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader

@Composable
fun MessageScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Nhắn tin", "personal")

        Column(
            modifier = Modifier.weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            MessageItemAdmin()
            MessageItemCustomer()
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp)
                .background(White)
                .border(1.dp, BlackAlpha10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(
                    text = "Soạn tin",
                    color = Color.Gray
                ) },
                modifier = Modifier.weight(1f)
                    .padding(Dimens.paddingMedium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = BlackAlpha30,
                    backgroundColor = White,
                    cursorColor = GreenPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = Typography.titleMedium
            )
            Image(
                painter = painterResource(R.drawable.paper_plane_solid),
                contentDescription = "Send",
                colorFilter = ColorFilter.tint(GreenPrimary),
                modifier = Modifier
                    .padding(end = Dimens.paddingMedium)
                    .size(Dimens.iconSizeMedium)
            )
        }
    }
}