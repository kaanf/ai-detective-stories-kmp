package com.kaanf.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.core.domain.validation.PasswordValidator
import com.kaanf.core.presentation.util.UIText
import com.kaanf.domain.EmailValidator

data class RegisterState(
    val emailTextState: TextFieldState = TextFieldState(),
    val rePasswordTextState: TextFieldState = TextFieldState(),
    val emailError: UIText? = null,
    val isMailExists: Boolean = false,
    val passwordTextState: TextFieldState = TextFieldState(),
    val passwordError: UIText? = null,
    val usernameTextState: TextFieldState = TextFieldState(),
    val usernameError: UIText? = null,
    val isUsernameExists: Boolean = false,
    val registrationError: UIText? = null,
    val isRegistering: Boolean = false,
    val isPasswordVisible: Boolean = false,
) {
    val isEmailValid: Boolean
        get() = EmailValidator.validate(emailTextState.text.toString())

    val isUsernameValid: Boolean
        get() = usernameTextState.text.length in 3..20

    val isPasswordValid: Boolean
        get() =
            PasswordValidator.validate(passwordTextState.text.toString())
                .isValidPassword
}
