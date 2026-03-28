package com.kaanf.auth.presentation.emailverification.verificationresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.auth.domain.repository.AuthRepository
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationResultViewModel(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val verificationToken = savedStateHandle.get<String>("token")

    private val eventChannel = Channel<EmailVerificationResultEvent>()
    val events
        get() = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(EmailVerificationResultState())
    val state = _state.asStateFlow()

    init {
        startVerification()
    }

    fun onAction(action: EmailVerificationResultAction) {
        when (action) {
            EmailVerificationResultAction.OnResendSignalClicked -> TODO()
            else -> Unit
        }
    }

    private fun startVerification() =
        viewModelScope.launch {
            val token = verificationToken

            if (token.isNullOrBlank()) {
                _state.update { current ->
                    current.copy(
                        phase = EmailVerificationPhase.Failed,
                    )
                }

                eventChannel.send(
                    EmailVerificationResultEvent.Message(
                        UIText.DynamicString("Verification token is missing."),
                    ),
                )

                return@launch
            }

            delay(1000L)

            authRepository
                .verifyEmail(token)
                .onSuccess {
                    _state.update { current ->
                        current.copy(
                            phase = EmailVerificationPhase.Verified,
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { current ->
                        current.copy(
                            phase = EmailVerificationPhase.Failed,
                        )
                    }

                    eventChannel.send(
                        EmailVerificationResultEvent.Message(
                            UIText.DynamicString(error.toUiText().toString()),
                        ),
                    )
                }
        }
}
