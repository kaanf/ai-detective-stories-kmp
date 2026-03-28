package com.kaanf.character.presentation.createcharacter

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.character.domain.model.avatar.UserAvatar
import com.kaanf.character.domain.model.avatar.UserAvatarList
import com.kaanf.character.domain.model.trait.TraitAllocation

data class CreateCharacterScreenState(
    val detectiveNameState: TextFieldState = TextFieldState(),
    val traitAllocations: List<TraitAllocation> = listOf(),
    val remainingTraitPoints: Int = INITIAL_UNALLOCATED_POINTS,
    val selectedAvatarId: String? = null,
    val isAvatarArchiveVisible: Boolean = false,
    val baseTraitValue: Int = 2,
    val avatars: UserAvatarList = UserAvatarList(images = listOf())
) {
    val isSubmitEnabled: Boolean
        get() = detectiveNameState.text.toString().trim().isNotEmpty() &&
            remainingTraitPoints == 0 &&
            selectedAvatarId != null

    val selectedAvatar: UserAvatar?
        get() = avatars.images.firstOrNull { avatar -> avatar.id == selectedAvatarId }

    companion object {
        const val BASE_TRAIT_VALUE = 2
        const val INITIAL_UNALLOCATED_POINTS = 5
        const val MAX_TRAIT_VALUE = BASE_TRAIT_VALUE + INITIAL_UNALLOCATED_POINTS
    }
}
