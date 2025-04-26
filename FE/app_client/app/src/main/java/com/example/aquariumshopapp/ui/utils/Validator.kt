package com.example.aquariumshopapp.ui.utils

import android.util.Patterns

class Validator {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }

        fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
            return password === confirmPassword
        }
    }
}