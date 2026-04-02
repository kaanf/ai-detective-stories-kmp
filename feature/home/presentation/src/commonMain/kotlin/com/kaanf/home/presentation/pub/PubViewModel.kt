package com.kaanf.home.presentation.pub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.repository.UserStore
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.domain.repository.PubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PubViewModel(
    private val userStore: UserStore,
    private val pubRepository: PubRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PubState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PubState(),
        )

    fun onAction(action: PubAction) {
        when (action) {
            is PubAction.OnItemActionClick -> Unit
        }
    }

    private fun loadInitialData() {
        observeUser()
        loadPubItems()
    }

    private fun observeUser() {
        viewModelScope.launch {
            userStore.observeCurrentUser().collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }

    private fun loadPubItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            pubRepository.getPubItems()
                .onSuccess { items ->
                    _state.update { it.copy(items = items) }
                }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
