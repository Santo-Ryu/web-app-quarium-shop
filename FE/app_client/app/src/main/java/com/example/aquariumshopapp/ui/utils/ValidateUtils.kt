package com.example.aquariumshopapp.ui.utils

import java.text.DecimalFormat

class ValidateUtils {
    companion object {
        fun formatPrice(price: String?): String {
            val formatter = DecimalFormat("#,###")
            return formatter.format(price?.toDouble() ?: 0.0) + " đ"
        }
    }
}