package com.example.aquarium_app.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.auth.components.AuthForm
import com.example.aquariumshopapp.ui.model.AuthUIState
import com.example.aquariumshopapp.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val authFieldsState by viewModel.authFieldsState.collectAsState()
    val authFieldsErrorState by viewModel.authFieldsErrorState.collectAsState()
    val currentForm by viewModel.currentForm.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    /*  Component  */
    AuthForm(
        authUIState =  AuthUIState(
            authFieldsState,
            authFieldsErrorState,
            currentForm
        ),
        onChangeFrom =  { formType -> viewModel.onSwitchForm(formType) },
        onInputChange =  { updatedState -> viewModel.onInputChanged(updatedState) },
        onButtonClick =  { coroutineScope.launch { viewModel.onButtonClick(context, navController) } }
    )
}