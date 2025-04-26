package com.example.aquarium_app.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.ERROR_RED
import com.example.aquarium_app.ui.theme.textError
import com.example.aquarium_app.ui.utils.WindowType
import com.example.aquarium_app.ui.theme.textFieldMedium

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier,

    iconId: Int,
    iconColor: Color,
    iconDescription: String,
    iconModifier: Modifier,

    placeholder: String,
    placeholderStyle: TextStyle,
    placeholderColor: Color,

    roundedCornerShape: Int,
    passField: Boolean,

    onValueChange: (String) -> Unit,
    value: String,

    windowType: WindowType,

    errorMessage: String
) {
    val modifierWType = when(windowType) {
        WindowType.Medium -> Modifier.height(60.dp)
        else -> Modifier.height(50.dp)
    }
    val styleText = when(windowType) {
        WindowType.Medium -> textFieldMedium
        else -> placeholderStyle
    }

    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = modifier.then(modifierWType)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                modifier = Modifier.fillMaxSize(),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = iconId),
                        colorFilter = ColorFilter.tint(iconColor),
                        contentDescription = iconDescription,
                        modifier = iconModifier
                    )
                },
                placeholder = {
                    Text(
                        placeholder,
                        style = styleText,
                        color = placeholderColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                visualTransformation =
                    if (passField) PasswordVisualTransformation() else VisualTransformation.None,
                shape = RoundedCornerShape(roundedCornerShape.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        /*  Label error  */
        Text(
            errorMessage,
            color = ERROR_RED,
            style = textError,
            modifier = Modifier.fillMaxWidth(.8f)
        )
    }
}