package com.kaanf.home.presentation.dispatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.domain.model.JobType
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.JobRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DispatchViewModel(
    private val caseRepository: CaseRepository,
    private val jobRepository: JobRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false
    private var countdownJob: Job? = null

    private val _state = MutableStateFlow(DispatchState())
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
            initialValue = DispatchState(),
        )

    fun onAction(action: DispatchAction) {
        when (action) {
            is DispatchAction.OnPickCase -> pickCase(action.caseId)
        }
    }

    private fun loadInitialData() {
        getTemporaryCases()
        fetchJobsAndStartCountdown()
    }

    private fun getTemporaryCases() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            caseRepository.getTemporaryCases()
                .onSuccess { temporaryQuests ->
                    _state.update { it.copy(cases = temporaryQuests.cases) }
                }
                .onFailure {
                    println("Error: ${it.name}")
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun pickCase(caseId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            caseRepository.pickCase(caseId)
                .onSuccess {
                    getTemporaryCases()
                }
                .onFailure {
                    println("")
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun fetchJobsAndStartCountdown() {
        viewModelScope.launch {
            jobRepository.getJobs()
                .onSuccess { jobs ->
                    val caseJob = jobs.jobs.firstOrNull { it.type == JobType.Case }
                    val seconds = caseJob?.timeUntilNextRunInSeconds() ?: 0
                    startCountdown(seconds)
                }
        }
    }

    private fun startCountdown(initialSeconds: Int) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            _state.update { it.copy(remainingSeconds = initialSeconds) }
            while (_state.value.remainingSeconds > 0) {
                delay(1_000L)
                _state.update { it.copy(remainingSeconds = it.remainingSeconds - 1) }
            }
        }
    }
}
