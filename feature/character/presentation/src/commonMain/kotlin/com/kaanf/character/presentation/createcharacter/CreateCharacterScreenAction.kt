package com.kaanf.character.presentation.createcharacter

import com.kaanf.character.domain.model.trait.CharacterTrait

sealed interface CreateCharacterScreenAction {
    data object OnUploadMugshotClick : CreateCharacterScreenAction
    data object OnDismissAvatarArchive : CreateCharacterScreenAction
    data object OnCreateCharacterClick : CreateCharacterScreenAction
    data class OnAvatarSelected(val avatarId: String) : CreateCharacterScreenAction
    data class OnIncreaseTrait(val trait: CharacterTrait) : CreateCharacterScreenAction
    data class OnDecreaseTrait(val trait: CharacterTrait) : CreateCharacterScreenAction
}
