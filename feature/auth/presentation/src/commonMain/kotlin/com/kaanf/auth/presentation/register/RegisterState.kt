package com.kaanf.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.core.domain.validation.PasswordValidator
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.domain.validation.EmailValidator

data class RegisterState(
    val emailTextState: TextFieldState = TextFieldState(),
    val rePasswordTextState: TextFieldState = TextFieldState(),
    val passwordTextState: TextFieldState = TextFieldState(),
    val isRegistering: Boolean = false,
    val isPasswordVisible: Boolean = false,
) {
    val isEmailValid: Boolean
        get() = EmailValidator.validate(emailTextState.text.toString())

    val isPasswordValid: Boolean
        get() =
            PasswordValidator.validate(passwordTextState.text.toString())
                .isValidPassword
}
