package com.example.aquariumshopapp.ui.screens.personal

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.enums.Gender
import com.example.aquariumshopapp.ui.screens.personal.components.PersonalHeader
import com.example.aquariumshopapp.ui.screens.personal.model.PersonalData
import com.example.aquariumshopapp.ui.screens.personal.PersonalViewModel

@Composable
fun PersonalInfoScreen(navController: NavController, viewModel: PersonalViewModel = viewModel() ) {
    val account = viewModel.customer.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    val personalDataList = listOf(
        PersonalData("Họ và tên", "${account.name}", "name"),
        PersonalData("Email", "${account.email}", "email", true),
        PersonalData("Số điện thoại", "${account.phone ?: "Chưa có"}", "phone"),
        PersonalData("Giới tính", "${getGender(account.gender.toString())}", "gender"),
        PersonalData("Ngày sinh", "${account.birthDate ?: "Chưa có"}", "birthDate"),
        PersonalData("Địa chỉ", "${account.address ?: "Chưa có"}", "address"),
        PersonalData("Tài khoản tạo vào lúc", "${account.createdAt}", "", true),
        PersonalData("Cập nhật gần đây", "${account.updatedAt}", "", true),
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

fun getGender(gender: String): String {
    if (gender == Gender.OTHER.toString()) return "Khác"
    if (gender == Gender.FEMALE.toString()) return "Nữ"
    if (gender == Gender.MALE.toString()) return "Nam"
    return "Invalid"
}