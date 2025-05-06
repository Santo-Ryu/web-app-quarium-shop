package com.example.aquarium_app.ui.screens.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aquarium_app.ui.common.CustomOutlinedTextField
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.domain.model.AuthFieldsState
import com.example.aquariumshopapp.ui.model.AuthUIState
import com.example.aquariumshopapp.ui.screens.auth.components.ExoVideoPlayer
import com.example.aquariumshopapp.ui.theme.modifierOutlinedTextFieldAuthForm

@Composable
fun AuthForm(
    authUIState: AuthUIState,
    onChangeFrom: (targetForm: String) -> Unit,
    onInputChange: (AuthFieldsState) -> Unit,
    onPrimaryButtonClicked: () -> Unit
) {
    val authFieldsState = authUIState.fields
    val authFieldsErrorState = authUIState.errors
    val authForm = authUIState.forms

    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        /*  Background video  */
        ExoVideoPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
        )

        /* Information filling form */
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(.7f)
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            topStartPercent = Dimens.borderRadiusSmall,
                            topEndPercent = Dimens.borderRadiusSmall,
                            bottomStartPercent = 0,
                            bottomEndPercent = 0
                        )
                    )
                    .background(Color.White),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*  Main form */
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spaceSmall, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /*  Logo  */
                    Image(
                        painter = painterResource(R.drawable.leaf_solid),
                        contentDescription = "Logo",
                        colorFilter = ColorFilter.tint(GreenPrimary),
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    /*  Title  */
                    Text(
                        authForm.title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = GreenPrimary
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    /*  Email  */
                    CustomOutlinedTextField(
                        Modifier.modifierOutlinedTextFieldAuthForm(),
                        R.drawable.user_solid,
                        GreenPrimary,
                        "Email",
                        Modifier.size(Dimens.iconSizeMedium),
                        "Email",
                        textFieldSmall,
                        BlackAlpha30,
                        30,
                        false,
                        { onInputChange(authFieldsState.copy(email = it)) },
                        authFieldsState.email,
                        authFieldsErrorState.emailError
                    )

                    /*  Password  */
                    if (authForm.type !== "forgot") {
                        CustomOutlinedTextField(
                            Modifier.modifierOutlinedTextFieldAuthForm(),
                            R.drawable.lock_solid,
                            GreenPrimary,
                            "Mật khẩu",
                            Modifier.size(Dimens.iconSizeMedium),
                            "Mật khẩu",
                            textFieldSmall,
                            BlackAlpha30,
                            30,
                            true,
                            { onInputChange(authFieldsState.copy(password = it)) },
                            authFieldsState.password,
                            authFieldsErrorState.passwordError
                        )
                    }

                    /* Confirm password */
                    if (authForm.type === "register") {
                        CustomOutlinedTextField(
                            Modifier.modifierOutlinedTextFieldAuthForm(),
                            R.drawable.lock_solid,
                            GreenPrimary,
                            "Xác nhận mật khẩu",
                            Modifier.size(Dimens.iconSizeMedium),
                            "Xác nhận mật khẩu",
                            textFieldSmall,
                            BlackAlpha30,
                            30,
                            true,
                            { onInputChange(authFieldsState.copy(confirmPassword = it)) },
                            authFieldsState.confirmPassword,
                            authFieldsErrorState.confirmPasswordError
                        )
                    }

                    /*  Button  */
                    Button(
                        onClick = {
                            if (authForm.type === "register") onPrimaryButtonClicked()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(Dimens.borderRadiusExtraLarge.dp))
                            .background(GreenPrimary),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(vertical = Dimens.paddingSmall)
                    ) {
                        Text(
                            authForm.label,
                            style = textButtonSmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.spaceSmall))

                    /*  Link to switch screens  */
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spaceSmall, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        /*  Link to register  */
                        Text(
                            text = buildAnnotatedString {
                                append(authForm.switchAccountPrompt[0] + " ")
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        color = GreenPrimary
                                    )
                                ) {
                                    append(authForm.switchAccountPrompt[1])
                                }
                            },
                            textAlign = TextAlign.Center,
                            style = Typography.bodyMedium,
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                when(authForm.type) {
                                    "login" -> onChangeFrom("register")
                                    "forgot" -> onChangeFrom("login")
                                    else -> onChangeFrom("login")
                                }
                            }
                        )
                        
                        /*  Link to forgot password  */
                        if (authForm.forgotPasswordPrompt.isNotEmpty()) {
                            Text(
                                authForm.forgotPasswordPrompt[0],
                                modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        onChangeFrom("forgot")
                                    },
                                textAlign = TextAlign.Center,
                                style = Typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}