package com.example.aquariumshopapp.ui.screens.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.datastore.TokenDataStore
import com.example.aquariumshopapp.data.enums.Authenticate
import com.example.aquariumshopapp.data.enums.Role
import com.example.aquariumshopapp.data.model.request.AccountCreateRequest
import com.example.aquariumshopapp.data.model.request.AuthenticateRequest
import com.example.aquariumshopapp.data.service.EncryptService
import com.example.aquariumshopapp.domain.model.AuthFieldsErrorState
import com.example.aquariumshopapp.domain.model.AuthFieldsState
import com.example.aquariumshopapp.ui.model.AuthForm
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import com.example.aquariumshopapp.ui.utils.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(): ViewModel() {
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

    fun clearAuthFieldState(): Unit {
        _authFieldsState.value = AuthFieldsState()
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

    suspend fun onButtonClick(context: Context, navController: NavController) {
        val type = currentForm.value.type
        Log.e("ON_BUTTON_CLICK", type)
        if (type.equals("register")) onRegisterClicked(context, navController)
        if (type.equals("login")) onLoginClicked(context, navController)
        if (type.equals("forgot")) onForgotClicked(context, navController)
    }

    //    EVENTS
    suspend fun onRegisterClicked(context: Context, navController: NavController) {
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
                !Validator.checkLengthPass(password) -> "Mật khẩu phải lớn hơn 6 ký tự"
                !Validator.isValidPassword(password) -> "Mật khẩu không hợp lệ!"
                else -> ""
            }
        }

        val confirmPasswordError: (String, String) -> String = {
            password, confirmPassword->
            when {
                isFieldEmpty(confirmPassword) -> "Xác nhận mật khẩu không được bỏ trống!"
                !Validator.checkLengthPass(confirmPassword) -> "Mật khẩu phải lớn hơn 6 ký tự"
                !Validator.doPasswordsMatch(password, confirmPassword) -> "Mật khẩu không trùng khớp!"
                !Validator.isValidPassword(confirmPassword) -> "Xác nhận mật khẩu không hợp lệ!"
                else -> ""
            }
        }

        _authFieldsErrorState.value = AuthFieldsErrorState(
            emailError(authFieldsState.email),
            passwordError(authFieldsState.password),
            confirmPasswordError(authFieldsState.password, authFieldsState.confirmPassword)
        )

        if (_authFieldsErrorState.value.emailError.isNotEmpty() ||
            _authFieldsErrorState.value.passwordError.isNotEmpty() ||
            _authFieldsErrorState.value.confirmPasswordError.isNotEmpty()) {
            Log.e("REGISTER", "Validation failed")
            return
        }

        val request = AccountCreateRequest(
            email = EncryptService.encrypt(authFieldsState.email),
            password = EncryptService.hashPassword(authFieldsState.password),
            role = EncryptService.encrypt(Role.CUSTOMER.toString())
        )

        try {
            val response = RetrofitClient.authInstance.register(request)
            if (response.isSuccessful) {
                val message = response.body()?.message
                NotificationUtils.showNotification(context, message.toString())
                _currentForm.value = AuthForm(
                                            "login",
                                            "Đăng nhập",
                                            "Đăng nhập",
                                            listOf("Bạn chưa có tài khoản?", "Đăng ký"),
                                            listOf("Quên mật khẩu?")
                                        )
            } else {
                Log.e("REGISTER_RESPONSE", "Error response: ${response.errorBody()?.string()}")
                NotificationUtils.showNotification(context, response.body()?.message.toString())
            }
            clearAuthFieldState()
        } catch (e: Exception) {
            Log.e("REGISTER_EXCEPTION", "Exception: ${e.message}", e)
            NotificationUtils.showNotification(context, "Lỗi kết nối: ${e.message}")
        }
    }

    suspend fun onLoginClicked(context: Context, navController: NavController) {
        val authFieldsState = authFieldsState.value

        val isFieldEmpty: (String) -> Boolean = { it.isBlank() }

        val emailError: (String) -> String = { email ->
            when {
                isFieldEmpty(email) -> "Email không được bỏ trống!"
                else -> ""
            }
        }

        val passwordError: (String) -> String = { password ->
            when {
                isFieldEmpty(password) -> "Mật khẩu không được bỏ trống!"
                else -> ""
            }
        }

        _authFieldsErrorState.value = AuthFieldsErrorState(
            emailError(authFieldsState.email),
            passwordError(authFieldsState.password)
        )

        if (_authFieldsErrorState.value.emailError.isNotEmpty() ||
            _authFieldsErrorState.value.passwordError.isNotEmpty()) {
            Log.e("LOGIN", "Validation failed")
            return
        }

        val request = AuthenticateRequest(
            email = EncryptService.encrypt(authFieldsState.email),
            password = EncryptService.hashPassword(authFieldsState.password),
            role = EncryptService.encrypt(Role.CUSTOMER.toString())
        )

        try {
            val response = RetrofitClient.authInstance.login(request)
            if (response.isSuccessful) {
                val message = response.body()?.message
                val customer = response.body()?.data?.account?.customer
                val token = response.body()?.data?.token
                val accountDataStore = AccountDataStore(context)
                val tokenDataStore = TokenDataStore(context)

                Log.i("LOGIN_SUCCESSFUL", message.toString())
                Log.i("LOGIN_SUCCESSFUL", customer.toString())

                accountDataStore.saveAccount(customer!!)
                tokenDataStore.saveToken(token!!)

                Log.i("JWT-TOKEN", token)
                RetrofitClient.setToken(token)

                NotificationUtils.showNotification(context, message.toString())

                navController.navigate("home")
            } else {
                // Kiểm tra nếu mã trạng thái là 401 (UNAUTHORIZED)
                if (response.code() == 401) {
                    val errorMessage = "Bạn chưa được phép truy cập. Vui lòng kiểm tra lại mật khẩu/email để xác thực (nếu có)."
                    Log.e("LOGIN_RESPONSE", "UNAUTHORIZED: $errorMessage")
                    NotificationUtils.showNotification(context, errorMessage)
                } else {
                    // Nếu không phải 401, in ra lỗi chung
                    val errorMessage = response.errorBody().toString() ?: "Lỗi không xác định"
                    Log.e("LOGIN_RESPONSE", "Error response: $errorMessage")
                    NotificationUtils.showNotification(context, errorMessage)
                }
            }
            clearAuthFieldState()
        } catch (e: Exception) {
            Log.e("LOGIN_EXCEPTION", "Exception: ${e.message}", e)
            NotificationUtils.showNotification(context, "Lỗi kết nối: ${e.message}")
        }
    }

    suspend fun onForgotClicked(context: Context, navController: NavController) {
        val authFieldsState = authFieldsState.value

        val isFieldEmpty: (String) -> Boolean = { it.isBlank() }

        val emailError: (String) -> String = { email ->
            when {
                isFieldEmpty(email) -> "Email không được bỏ trống!"
                else -> ""
            }
        }

        _authFieldsErrorState.value = AuthFieldsErrorState(
            emailError(authFieldsState.email)
        )

        if (_authFieldsErrorState.value.emailError.isNotEmpty()) {
            Log.e("FORGOT", "Validation failed")
            return
        }

        val request = AuthenticateRequest(
            email = EncryptService.encrypt(authFieldsState.email),
            role = EncryptService.encrypt(Role.CUSTOMER.toString())
        )

        try {
            val response = RetrofitClient.authInstance.forgot(request)
            if (response.isSuccessful) {
                val message = response.body()?.message
                NotificationUtils.showNotification(context, message.toString())
                _currentForm.value = AuthForm(
                    "login",
                    "Đăng nhập",
                    "Đăng nhập",
                    listOf("Bạn chưa có tài khoản?", "Đăng ký"),
                    listOf("Quên mật khẩu?")
                )
            } else {
                Log.e("FORGOT_RESPONSE", "Error response: ${response.errorBody()?.string()}")
                NotificationUtils.showNotification(context, response.body()?.message.toString())
            }
            clearAuthFieldState()
        } catch (e: Exception) {
            Log.e("FORGOT_EXCEPTION", "Exception: ${e.message}", e)
            NotificationUtils.showNotification(context, "Lỗi kết nối: ${e.message}")
        }
    }
}