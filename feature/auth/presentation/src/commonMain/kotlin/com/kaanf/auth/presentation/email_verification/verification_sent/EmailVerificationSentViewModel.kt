package com.kaanf.auth.presentation.email_verification.verification_sent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmailVerificationSentViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val verificationToken = savedStateHandle.get<String>("token")

    private val eventChannel = Channel<EmailVerificationSentEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state =
        MutableStateFlow(
            EmailVerificationSentState(
            ),
        )
    val state =
        _state.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _state.value,
        )

    init {
        if (verificationToken != null) {
        }
    }

    fun onAction(action: EmailVerificationSentAction) {
        when (action) {
            EmailVerificationSentAction.OnResendSignalClick -> {}
            EmailVerificationSentAction.OnReturnToTerminalClick -> {
                viewModelScope.launch {
                    eventChannel.send(EmailVerificationSentEvent.NavigateToTerminal)
                }
            }
        }
    }
}
