package com.kaanf.home.presentation.dispatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.home.domain.model.JobType
import com.kaanf.home.domain.repository.CaseRepository
import com.kaanf.home.domain.repository.JobRepository
import com.kaanf.home.domain.usecase.PickCaseAndSyncUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

class DispatchViewModel(
    private val caseRepository: CaseRepository,
    private val jobRepository: JobRepository,
    private val pickCaseAndSyncUseCase: PickCaseAndSyncUseCase,
) : ViewModel() {
    private var countdownJob: Job? = null

    private val remainingSeconds = MutableStateFlow(0)

    private val isLoading = MutableStateFlow(false)

    val state = combine(
        isLoading,
        caseRepository.observeTemporaryCases(),
        remainingSeconds,
    ) { isLoading, temporaryCases, remainingSeconds ->
        DispatchState(
            cases = temporaryCases,
            isLoading = isLoading,
            remainingSeconds = remainingSeconds,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = DispatchState(),
    )

    init {
        getJob()
        getTemporaryCases()
    }

    private fun getJob() = viewModelScope.launch {
        jobRepository.getJobs()
            .onSuccess { jobs ->
                val caseJob = jobs.jobs.firstOrNull { it.type == JobType.Case }
                startCountdown(caseJob?.timeUntilNextRunInSeconds() ?: 0)
            }
    }

    private fun getTemporaryCases() = viewModelScope.launch {
        isLoading.value = true
        caseRepository.getTemporaryCases()
            .onSuccess {
                isLoading.value = false
            }
            .onFailure {
                isLoading.value = false
            }
    }

    fun onAction(action: DispatchAction) {
        when (action) {
            is DispatchAction.OnPickCase -> pickCase(action.caseId)
        }
    }

    private fun pickCase(caseId: String) {
        viewModelScope.launch {
            pickCaseAndSyncUseCase(caseId)
                .onSuccess {}
                .onFailure {
                }
        }
    }

    private fun startCountdown(initialSeconds: Int) {
        countdownJob?.cancel()

        val targetMark = TimeSource.Monotonic.markNow() + initialSeconds.seconds

        countdownJob = viewModelScope.launch {
            while (true) {
                val remaining = -targetMark.elapsedNow()
                val secondsLeft = max(0, ceil(remaining.inWholeMilliseconds / 1000.0).toInt())

                remainingSeconds.value = secondsLeft

                if (targetMark.hasPassedNow()) break

                delay(250L)
            }
        }
    }
}
