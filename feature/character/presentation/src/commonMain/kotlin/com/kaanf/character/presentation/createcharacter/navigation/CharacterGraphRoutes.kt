package com.kaanf.character.presentation.createcharacter.navigation

import kotlinx.serialization.Serializable

sealed interface CharacterGraphRoutes {
    @Serializable
    data object Graph: CharacterGraphRoutes

    @Serializable
    data object CreateCharacter: CharacterGraphRoutes
}
