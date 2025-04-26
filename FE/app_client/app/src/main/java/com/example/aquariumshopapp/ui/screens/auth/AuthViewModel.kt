package com.example.aquariumshopapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.domain.model.AuthFieldsErrorState
import com.example.aquariumshopapp.domain.model.AuthFieldsState
import com.example.aquariumshopapp.ui.model.auth.AuthForm
import com.example.aquariumshopapp.ui.utils.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.toString

class AuthViewModel: ViewModel() {
    private val _authFieldsState = MutableStateFlow(AuthFieldsState())
    val authFieldsState: StateFlow<AuthFieldsState> = _authFieldsState

    private val _authFieldsErrorState = MutableStateFlow(AuthFieldsErrorState())
    val authFieldsErrorState: StateFlow<AuthFieldsErrorState> = _authFieldsErrorState

    private val _currentForm = MutableStateFlow(
        AuthForm(
            type = "login",
            title = "Đăng nhập",
            label = "Đăng nhập",
            switchAccountPrompt = listOf("Bạn chưa có tài khoản?", "Đăng ký"),
            forgotPasswordPrompt = listOf("Quên mật khẩu?")
        )
    )
    val currentForm: StateFlow<AuthForm> = _currentForm

    fun onSwitchForm(type: String) {
        _currentForm.value = getAuthForm(type)
        _authFieldsState.value = AuthFieldsState()
        _authFieldsErrorState.value = AuthFieldsErrorState()
    }

    fun onInputChanged(updatedState: AuthFieldsState) {
        _authFieldsState.value = updatedState
    }

    private fun getAuthForm(type: String): AuthForm {
        return when (type) {
            "login" -> AuthForm(
                "login",
                "Đăng nhập",
                "Đăng nhập",
                listOf("Bạn chưa có tài khoản?", "Đăng ký"),
                listOf("Quên mật khẩu?")
            )
            "register" -> AuthForm(
                "register",
                "Đăng ký",
                "Đăng ký",
                listOf("Bạn đã có tài khoản?", "Đăng nhập")
            )
            "forgot" -> AuthForm(
                "forgot",
                "Quên mật khẩu",
                "Gửi mật khẩu mới",
                listOf("Bạn đã nhớ mật khẩu?", "Đăng nhập")
            )
            else -> AuthForm(
                "login",
                "Đăng nhập",
                "Đăng nhập",
                listOf("Bạn chưa có tài khoản?", "Đăng ký"),
                listOf("Quên mật khẩu?")
            )
        }
    }

    //    EVENTS
    fun onRegisterClicked() {
        val authFieldsState = authFieldsState.value

        val isFieldEmpty: (String) -> Boolean = { it.isBlank() }

        val emailError: (String) -> String = { email ->
            when {
                isFieldEmpty(email) -> "Email không được bỏ trống!"
                !Validator.isValidEmail(email) -> "Email không hợp lệ!"
                else -> ""
            }
        }

        val passwordError: (String) -> String = { password ->
            when {
                isFieldEmpty(password) -> "Mật khẩu không được bỏ trống!"
                !Validator.isValidPassword(password) -> "Mật khẩu không hợp lệ!"
                else -> ""
            }
        }

        val confirmPasswordError: (String, String) -> String = {
            password, confirmPassword->
            when {
                isFieldEmpty(confirmPassword) -> "Xác nhận mật khẩu không được bỏ trống!"
                Validator.doPasswordsMatch(password, confirmPassword) == false -> "Mật khẩu không trùng khớp!"
                !Validator.isValidPassword(confirmPassword) -> "Xác nhận mật khẩu không hợp lệ!"
                else -> ""
            }
        }

        _authFieldsErrorState.value = AuthFieldsErrorState(
            emailError(authFieldsState.email),
            passwordError(authFieldsState.password),
            confirmPasswordError(authFieldsState.password, authFieldsState.confirmPassword)
        )
    }

    fun onLoginClicked() {

    }
    fun onForgotClicked() {

    }
}