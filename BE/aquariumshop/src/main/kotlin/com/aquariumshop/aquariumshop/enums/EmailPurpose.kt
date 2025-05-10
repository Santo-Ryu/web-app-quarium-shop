package com.aquariumshop.aquariumshop.enums

enum class EmailPurpose {
    ACCOUNT_VERIFICATION,
    PASSWORD_RESET;

    override fun toString(): String {
        return when (this) {
            ACCOUNT_VERIFICATION -> "Registration"
            PASSWORD_RESET -> "Reset Password"
        }
    }
}