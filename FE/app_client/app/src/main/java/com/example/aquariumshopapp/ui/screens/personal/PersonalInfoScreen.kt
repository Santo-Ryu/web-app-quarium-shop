package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import com.example.aquariumshopapp.ui.screens.personal.model.PersonalData

@Composable
fun PersonalInfoScreen(navController: NavController) {
    val personalDataList = listOf(
        PersonalData("Họ và tên", "Santo Le", "name"),
        PersonalData("Email", "abc@gmail.com", "email", true),
        PersonalData("Số điện thoại", "0354337115", "phone"),
        PersonalData("Giới tính", "Nam", "gender"),
        PersonalData("Ngày sinh", "2005-02-22", "birthDate"),
        PersonalData("Địa chỉ", "", "address"),
        PersonalData("Tài khoản tạo vào lúc", "2005-06-20", "", true),
        PersonalData("Cập nhật gần đây", "2005-06-20", "", true),
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PersonalHeader(navController, "Thông tin cá nhân", "personal")

        personalDataList.forEach { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.field,
                    modifier = Modifier
                        .padding(Dimens.paddingMedium)
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.value,
                        modifier = Modifier
                            .padding(Dimens.paddingMedium),
                        color = Color.Gray,
                        textDecoration = TextDecoration.Underline
                    )
                    if (item.hiddenIcon == false) {
                        Image(
                            painter = painterResource(R.drawable.caret_right_solid),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .padding(end = Dimens.paddingMedium)
                                .size(Dimens.iconSizeESmall)
                                .clickable {
                                    item.hiddenIcon?.let {
                                        if (!it) {
                                            navController.navigate("personal_update/${item.fieldNavigate}")
                                        }
                                    }
                                }
                        )
                    }
                }
            }
            Divider()
        }
    }
}