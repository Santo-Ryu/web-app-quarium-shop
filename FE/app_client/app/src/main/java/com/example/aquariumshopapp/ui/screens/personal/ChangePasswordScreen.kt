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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: PersonalViewModel = viewModel()) {
    val listField = listOf("Mật khẩu hiện tại", "Mật khẩu mới", "Xác nhận mật khẩu")
    val context = LocalContext.current
    val isLoading = viewModel.isDataLoaded.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PersonalHeader(navController, "Đổi mật khẩu", "personal")

            listField.forEachIndexed { index, item ->
                OutlinedTextField(
                    value = when (index) {
                        0 -> currentPassword
                        1 -> newPassword
                        2 -> confirmPassword
                        else -> ""
                    },
                    onValueChange = { value ->
                        when (index) {
                            0 -> currentPassword = value
                            1 -> newPassword = value
                            2 -> confirmPassword = value
                        }
                    },
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
                onClick = {
                    coroutineScope.launch {
                        viewModel.changePassword(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword,
                            navController = navController,
                            context = context
                        )
                    }
                },
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
}