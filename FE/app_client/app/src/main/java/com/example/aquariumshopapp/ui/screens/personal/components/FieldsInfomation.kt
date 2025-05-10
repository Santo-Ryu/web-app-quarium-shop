package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.personal.model.FieldsInfoItem

@Composable
fun FieldsInformation(
    fields: List<FieldsInfoItem>,
    navController: NavController
) {
    fields.forEach { item ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(item.navigate) }
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .padding(Dimens.paddingMedium)
            )
            if (item.hiddenIcon == false) {
                Image(
                    painter = painterResource(R.drawable.caret_right_solid),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .padding(end = Dimens.paddingMedium)
                        .size(Dimens.iconSizeESmall)
                )
            }
        }
        Divider()
    }
}