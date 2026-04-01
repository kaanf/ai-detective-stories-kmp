package com.kaanf.character.presentation.createcharacter

import com.kaanf.core.presentation.base.BaseEvent

sealed interface CreateCharacterEvent: BaseEvent {
    data object NavigateToHome : CreateCharacterEvent
}
