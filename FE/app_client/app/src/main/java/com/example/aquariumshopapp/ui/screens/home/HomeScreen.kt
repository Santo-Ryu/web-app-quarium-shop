package com.example.aquariumshopapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.screens.home.components.Header
import com.example.aquarium_app.ui.screens.home.components.MainContent
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.White

@Composable
fun HomeScreen() {
    /*  Background  */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
    ) {
        /*  Modifiers  */
        val barModifier = Modifier
            .fillMaxWidth()
            .weight(.1f)
            .background(White)

        val mainContentModifier = Modifier
            .fillMaxWidth()
            .weight(.8f)

        val cardModifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp, BlackAlpha10,
                shape = RoundedCornerShape(8.dp)
            )
            .height(250.dp)
            .weight(1f)

        /*  Header  */
        Header(barModifier)

        /*  Main  */
        MainContent(mainContentModifier, cardModifier)

        /*  Navigation bar  */
        NavigationBar(barModifier)
    }
}