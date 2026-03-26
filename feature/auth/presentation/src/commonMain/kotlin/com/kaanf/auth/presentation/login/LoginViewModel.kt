package com.kaanf.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.Result
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.presentation.util.toUiText
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.error_invalid_email
import detective_ai_stories.feature.auth.presentation.generated.resources.error_invalid_password
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService,
) : ViewModel() {
    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state =
        _state.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState(),
        )

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> {
                login()
            }

            LoginAction.OnRegisterClick -> {
                viewModelScope.launch {
                    eventChannel.send(LoginEvent.NavigateToRegister)
                }
            }
        }
    }

    private fun login() =
        viewModelScope.launch {
            val currentState = _state.value

            if (currentState.isSubmitting) {
                return@launch
            }

            if (!currentState.isEmailValid) {
                eventChannel.send(
                    LoginEvent.Failure(
                        UIText.Resource(Res.string.error_invalid_email),
                    ),
                )
                return@launch
            }

            if (!currentState.isPasswordValid) {
                eventChannel.send(
                    LoginEvent.Failure(
                        UIText.Resource(Res.string.error_invalid_password),
                    ),
                )
                return@launch
            }

            _state.update {
                it.copy(
                    isSubmitting = true,
                )
            }

            try {
                when (
                    val result =
                        authService.login(
                            email = currentState.emailTextState.text.toString(),
                            password = currentState.passwordTextState.text.toString(),
                        )
                ) {
                    is Result.Success -> {
                        eventChannel.send(LoginEvent.Success)
                    }

                    is Result.Failure -> {
                        eventChannel.send(
                            LoginEvent.Failure(
                                result.error.toUiText(),
                            ),
                        )
                    }
                }
            } finally {
                _state.update {
                    it.copy(
                        isSubmitting = false,
                    )
                }
            }
        }
}
