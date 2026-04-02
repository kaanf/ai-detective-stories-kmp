package com.kaanf.home.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.repository.UserStore
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.domain.repository.CaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val userStore: UserStore,
    private val caseRepository: CaseRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(DashboardState())
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
            initialValue = DashboardState(),
        )

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OnCaseClick -> Unit
        }
    }

    private fun loadInitialData() {
        observeUser()
        loadCases()
    }

    private fun observeUser() {
        viewModelScope.launch {
            userStore.observeCurrentUser().collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }

    private fun loadCases() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            caseRepository.getCases()
                .onSuccess { userCases ->
                    _state.update { it.copy(cases = userCases.cases) }
                }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
