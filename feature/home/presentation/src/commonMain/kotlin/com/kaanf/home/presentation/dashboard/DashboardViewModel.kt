package com.kaanf.home.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.repository.UserRepository
import com.kaanf.core.domain.repository.UserStore
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.CaseStore
import io.ktor.util.Hash.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val caseRepository: CaseRepository,
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)

    val state = combine(
        caseRepository.observeUserCases(),
        isLoading,
    ) { cases, isLoading ->
        DashboardState(
            cases = cases,
            isLoading = isLoading,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = DashboardState(),
    )

    init {
        getCases()
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OnCaseClick -> Unit
        }
    }

    private fun getCases() = viewModelScope.launch {
        isLoading.value = true
        caseRepository.getCases()
            .onSuccess {
                isLoading.value = false
            }
            .onFailure {
                isLoading.value = false
            }
    }
}
