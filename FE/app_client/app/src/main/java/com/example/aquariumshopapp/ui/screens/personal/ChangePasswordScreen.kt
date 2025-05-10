package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val listField = listOf("Mật khẩu hiện tại", "Mật khẩu mới", "Xác nhận mật khẩu")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Đổi mật khẩu", "personal")

        listField.forEach { item ->
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text(item) },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = Dimens.paddingMedium, end = Dimens.paddingMedium, top = Dimens.paddingMedium),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = BlackAlpha30,
                    backgroundColor = BlackAlpha10,
                    cursorColor = GreenPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = Typography.titleMedium
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor  = GreenPrimary,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Hoàn thành",
                modifier = Modifier.padding(8.dp),
                style = Typography.titleLarge,
                color = White
            )
        }
    }
}