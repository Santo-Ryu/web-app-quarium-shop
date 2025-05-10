package com.example.aquariumshopapp.ui.screens.personal

import android.R.attr.textStyle
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquarium_app.ui.theme.textError
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import java.util.Calendar

@Composable
fun PersonalUpdateScreen(navController: NavController) {
    val context = LocalContext.current
    val fieldName = navController.currentBackStackEntry?.arguments?.getString("fieldNavigate") ?: ""

    val changeParamsToTile = when (fieldName) {
        "name" -> "họ và tên"
        "gender" -> "giới tính"
        "phone" -> "số điện thoại"
        "email" -> "email"
        "birthDate" -> "ngày sinh"
        else -> "Valid"
    }

    var valueTextField by remember{ mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Cập nhật ${changeParamsToTile}", "personal_info")

        if (fieldName.equals("name") || fieldName.equals("phone") || fieldName.equals("email") || fieldName.equals("address")) {
            OutlinedTextField(
                value = valueTextField,
                onValueChange = { valueTextField = it},
                modifier = Modifier.fillMaxWidth()
                    .padding(Dimens.paddingMedium),
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

        if (fieldName.equals("gender")) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingMedium)
            ) {
                Text(
                    text = "Chọn giới tính",
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Nam",
                            onClick = { selectedGender = "Nam" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = GreenPrimary,
                                unselectedColor = BlackAlpha30
                            )
                        )
                        Text(
                            text = "Nam",
                            style = Typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Nữ",
                            onClick = { selectedGender = "Nữ" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = GreenPrimary,
                                unselectedColor = BlackAlpha30
                            )
                        )
                        Text(
                            text = "Nữ",
                            style = Typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        if (fieldName.equals("birthDate")) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingMedium)
            ) {
                Text(
                    text = "Chọn ngày sinh",
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = GreenPrimary,
                        unfocusedBorderColor = BlackAlpha30,
                        backgroundColor = BlackAlpha10,
                        cursorColor = GreenPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = Typography.titleMedium,
                    enabled = false
                )
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.paddingMedium, end = Dimens.paddingMedium),
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