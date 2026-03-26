package com.kaanf.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.core.domain.validation.PasswordValidator
import com.kaanf.core.domain.validation.EmailValidator

data class LoginState(
    val emailTextState: TextFieldState = TextFieldState(),
    val passwordTextState: TextFieldState = TextFieldState(),
    val isSubmitting: Boolean = false,
) {
    val isEmailValid: Boolean
        get() = EmailValidator.validate(emailTextState.text.toString())

    val isPasswordValid: Boolean
        get() =
            PasswordValidator.validate(passwordTextState.text.toString())
                .isValidPassword
}
