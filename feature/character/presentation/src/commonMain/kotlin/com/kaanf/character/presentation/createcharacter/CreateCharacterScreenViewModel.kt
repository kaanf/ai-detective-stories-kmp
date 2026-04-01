package com.kaanf.character.presentation.createcharacter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.character.domain.model.trait.CharacterTrait
import com.kaanf.character.domain.repository.CharacterRepository
import com.kaanf.core.domain.repository.UserRepository
import com.kaanf.core.domain.util.onFailure
import com.kaanf.core.domain.util.onSuccess
import com.kaanf.core.presentation.base.BaseEvent
import com.kaanf.core.presentation.model.SnackbarMessage
import com.kaanf.core.presentation.model.SnackbarVariant
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.presentation.util.toUiText
import detective_ai_stories.feature.character.presentation.generated.resources.Res
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_snackbar_invalid_name_description
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_snackbar_invalid_name_title
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_snackbar_no_avatar_description
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_snackbar_no_avatar_title
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreateCharacterScreenViewModel(
    private val userRepository: UserRepository,
    private val characterRepository: CharacterRepository,
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

    private val eventChannel = Channel<BaseEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getAvatars()
    }

    fun onAction(action: CreateCharacterScreenAction) {
        when (action) {
            CreateCharacterScreenAction.OnCreateCharacterClick -> createCharacter()
            CreateCharacterScreenAction.OnDismissAvatarArchive -> {
                _state.value = _state.value.copy(isAvatarArchiveVisible = false)
            }

            CreateCharacterScreenAction.OnUploadMugshotClick -> {
                _state.value = _state.value.copy(isAvatarArchiveVisible = true)
            }

            is CreateCharacterScreenAction.OnAvatarSelected -> {
                _state.value = _state.value.copy(
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

    private fun createCharacter() = viewModelScope.launch {
        val currentState = _state.value

        if (currentState.detectiveNameState.text.toString().trim().isEmpty()) {
            eventChannel.send(
                BaseEvent.ShowSnackbar(
                    SnackbarMessage(
                        title = UIText.Resource(Res.string.character_creation_snackbar_invalid_name_title),
                        description = UIText.Resource(Res.string.character_creation_snackbar_invalid_name_description),
                        variant = SnackbarVariant.Failure,
                    ),
                ),
            )
            return@launch
        }

        if (currentState.selectedAvatarId == null) {
            eventChannel.send(
                BaseEvent.ShowSnackbar(
                    SnackbarMessage(
                        title = UIText.Resource(Res.string.character_creation_snackbar_no_avatar_title),
                        description = UIText.Resource(Res.string.character_creation_snackbar_no_avatar_description),
                        variant = SnackbarVariant.Failure,
                    ),
                ),
            )
            return@launch
        }

        val currentUser = userRepository.observeCurrentUser().first() ?: return@launch

        _state.value = currentState.copy(isLoading = true)

        userRepository.updateUser(
            currentUser.copy(
                fullName = currentState.detectiveNameState.text.toString().trim(),
                profileImageUrl = currentState.selectedAvatar?.avatarImageUrl
                    ?.substringAfterLast("/")
                    ?.substringBeforeLast(".")
                    ?: currentUser.profileImageUrl,
            ),
        )
            .onSuccess {
                eventChannel.send(CreateCharacterEvent.NavigateToHome)
            }
            .onFailure {
                _state.value = _state.value.copy(isLoading = false)

                eventChannel.send(
                    BaseEvent.ShowSnackbar(
                        SnackbarMessage(
                            title = it.toUiText(),
                            description = it.toUiText(),
                            variant = SnackbarVariant.Warning,
                        ),
                    ),
                )
            }
    }

    private fun getAvatars() = viewModelScope.launch {
        val currentState = state.value

        characterRepository.getAvatars()
            .onSuccess { data ->
                _state.value = currentState.copy(avatars = data)
            }
            .onFailure {
                // Todo: handle error.
            }
    }

    private fun updateTraitValue(trait: CharacterTrait, delta: Int) {
        val currentState = _state.value
        val currentAllocation =
            currentState.traitAllocations.firstOrNull { it.trait == trait } ?: return

        if (delta > 0 && currentState.remainingTraitPoints <= 0) return
        if (delta < 0 && currentAllocation.value <= CreateCharacterScreenState.BASE_TRAIT_VALUE) return

        val updatedValue = (currentAllocation.value + delta).coerceIn(
            minimumValue = CreateCharacterScreenState.BASE_TRAIT_VALUE,
            maximumValue = CreateCharacterScreenState.MAX_TRAIT_VALUE,
        )

        if (updatedValue == currentAllocation.value) return

        _state.value = currentState.copy(
            traitAllocations = currentState.traitAllocations.map { allocation ->
                if (allocation.trait == trait) allocation.copy(value = updatedValue) else allocation
            },
            remainingTraitPoints = (currentState.remainingTraitPoints - delta)
                .coerceIn(0, CreateCharacterScreenState.INITIAL_UNALLOCATED_POINTS),
        )
    }
}
