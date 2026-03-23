package com.kaanf.auth.presentation.forgot_password

import androidx.compose.foundation.text.input.TextFieldState

data class ForgotPasswordState(
    val emailTextState: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false
)
