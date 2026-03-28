package com.kaanf.home.presentation.createcharacter

import androidx.compose.foundation.text.input.TextFieldState
import com.kaanf.core.domain.home.UserAvatar
import com.kaanf.core.domain.home.UserAvatarList

enum class CharacterTrait(
    val title: String,
    val description: String,
) {
    Investigation(
        title = "INVESTIGATION",
        description = "Finding clues in the dark.",
    ),
    Brutality(
        title = "BRUTALITY",
        description = "Physical force and intimidation.",
    ),
    Perception(
        title = "PERCEPTION",
        description = "Reading lies and hidden motives.",
    ),
    Willpower(
        title = "WILLPOWER",
        description = "Resisting the city's madness.",
    ),
}

data class TraitAllocation(
    val trait: CharacterTrait,
    val value: Int,
)

data class AvatarArchiveItemUiModel(
    val id: String,
    val imageUrl: String,
) {
    val selectionLabel: String
        get() = "SELECT $id"

    val dossierLabel: String
        get() = "VISUAL $id"
}

data class CreateCharacterScreenState(
    val detectiveNameState: TextFieldState = TextFieldState(),
    val traitAllocations: List<TraitAllocation> =
        CharacterTrait.entries.map { trait ->
            TraitAllocation(
                trait = trait,
                value = BaseTraitValue,
            )
        },
    val remainingTraitPoints: Int = InitialUnallocatedPoints,
    val selectedAvatarId: String? = null,
    val isAvatarArchiveVisible: Boolean = false,
    val avatars: UserAvatarList = UserAvatarList(images = listOf())
) {
    val isSubmitEnabled: Boolean
        get() = detectiveNameState.text.toString().trim().isNotEmpty() &&
            remainingTraitPoints == 0 &&
            selectedAvatarId != null

    val selectedAvatar: UserAvatar?
        get() = avatars.images.firstOrNull { avatar -> avatar.id == selectedAvatarId }

    companion object {
        const val BaseTraitValue = 2
        const val InitialUnallocatedPoints = 5
        const val MaxTraitValue = BaseTraitValue + InitialUnallocatedPoints
    }
}

private fun defaultAvatarArchiveItems() =
    listOf(
        AvatarArchiveItemUiModel(
            id = "ID-1",
            imageUrl = "https://www.figma.com/api/mcp/asset/67cbd908-32c3-4acc-a0a0-d4517e0a384f",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-2",
            imageUrl = "https://www.figma.com/api/mcp/asset/8d40b994-4cc9-4e11-a79e-553101d680f8",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-3",
            imageUrl = "https://www.figma.com/api/mcp/asset/67da3bc1-a720-4cc4-97b0-24a79d363393",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-4",
            imageUrl = "https://www.figma.com/api/mcp/asset/32db5955-77bf-483f-a23c-99a5c5df0122",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-5",
            imageUrl = "https://www.figma.com/api/mcp/asset/e574556f-1ab2-42e8-bd17-df67f609a1b7",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-6",
            imageUrl = "https://www.figma.com/api/mcp/asset/fc97a357-437e-4777-a5b2-d9a910ea08cc",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-7",
            imageUrl = "https://www.figma.com/api/mcp/asset/12133327-24c5-4778-be69-c9a23494e946",
        ),
        AvatarArchiveItemUiModel(
            id = "ID-8",
            imageUrl = "https://www.figma.com/api/mcp/asset/4e85f408-f4a1-44fd-a9e1-74296a930e60",
        ),
    )
