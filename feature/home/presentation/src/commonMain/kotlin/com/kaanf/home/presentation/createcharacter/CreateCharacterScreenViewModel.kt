package com.kaanf.home.presentation.createcharacter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.home.HomeService
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreateCharacterScreenViewModel(
    private val homeService: HomeService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CreateCharacterScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateCharacterScreenState(),
        )

    init {
        getAvatars()
    }

    fun onAction(action: CreateCharacterScreenAction) {
        when (action) {
            CreateCharacterScreenAction.OnCreateCharacterClick -> Unit
            CreateCharacterScreenAction.OnDismissAvatarArchive -> {
                _state.value =
                    _state.value.copy(
                        isAvatarArchiveVisible = false,
                    )
            }
            CreateCharacterScreenAction.OnUploadMugshotClick -> {
                _state.value =
                    _state.value.copy(
                        isAvatarArchiveVisible = true,
                    )
            }
            is CreateCharacterScreenAction.OnAvatarSelected -> {
                _state.value =
                    _state.value.copy(
                        selectedAvatarId = action.avatarId,
                        isAvatarArchiveVisible = false,
                    )
            }
            is CreateCharacterScreenAction.OnDecreaseTrait -> updateTraitValue(
                trait = action.trait,
                delta = -1,
            )
            is CreateCharacterScreenAction.OnIncreaseTrait -> updateTraitValue(
                trait = action.trait,
                delta = 1,
            )
        }
    }

    private fun getAvatars() = viewModelScope.launch {
        val currentState = state.value

        homeService.getAvatars()
            .onSuccess { data ->
                _state.value = currentState.copy(
                    avatars = data
                )
            }
            .onFailure {
                // Todo: handle error.
            }
    }

    private fun updateTraitValue(
        trait: CharacterTrait,
        delta: Int,
    ) {
        val currentState = _state.value
        val currentAllocation =
            currentState.traitAllocations.firstOrNull { allocation ->
                allocation.trait == trait
            } ?: return

        if (delta > 0 && currentState.remainingTraitPoints <= 0) {
            return
        }

        if (delta < 0 && currentAllocation.value <= CreateCharacterScreenState.BaseTraitValue) {
            return
        }

        val updatedValue = (currentAllocation.value + delta)
            .coerceIn(
                minimumValue = CreateCharacterScreenState.BaseTraitValue,
                maximumValue = CreateCharacterScreenState.MaxTraitValue,
            )

        if (updatedValue == currentAllocation.value) {
            return
        }

        _state.value =
            currentState.copy(
                traitAllocations =
                    currentState.traitAllocations.map { allocation ->
                        if (allocation.trait == trait) {
                            allocation.copy(value = updatedValue)
                        } else {
                            allocation
                        }
                    },
                remainingTraitPoints =
                    (currentState.remainingTraitPoints - delta)
                        .coerceIn(0, CreateCharacterScreenState.InitialUnallocatedPoints),
            )
    }
}
