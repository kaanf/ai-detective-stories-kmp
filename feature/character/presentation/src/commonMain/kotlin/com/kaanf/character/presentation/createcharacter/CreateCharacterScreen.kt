package com.kaanf.character.presentation.createcharacter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.character.presentation.createcharacter.component.avatar.AvatarGrid
import com.kaanf.core.presentation.util.ObserveAsEvents
import com.kaanf.character.presentation.createcharacter.component.trait.TraitAllocationAddCard
import com.kaanf.character.presentation.createcharacter.component.avatar.SelectAvatarField
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.BaseBottomSheetContainer
import com.kaanf.core.designsystem.component.layout.LoadingOverlayLayout
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.designsystem.component.textfield.BaseTextField
import com.kaanf.core.designsystem.component.title.AccessHeader
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.base.BaseEvent
import com.kaanf.core.presentation.util.TestTags
import detective_ai_stories.feature.character.presentation.generated.resources.Res
import detective_ai_stories.feature.character.presentation.generated.resources.avatar_sheet_footer_instruction
import detective_ai_stories.feature.character.presentation.generated.resources.avatar_sheet_title
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_name_label
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_name_placeholder
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_primary_action_initialize_dossier
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_subtitle
import detective_ai_stories.feature.character.presentation.generated.resources.character_creation_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateCharacterRoot(
    viewModel: CreateCharacterScreenViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BaseEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(event.snackbarMessage)
            }

            CreateCharacterEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    SnackbarScaffold(snackbarHostState) { innerPadding ->
        LoadingOverlayLayout(
            isLoading = state.isLoading,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .testTag(TestTags.CHARACTER_SCREEN),
        ) {
            CreateCharacterScreen(
                state = state,
                onAction = viewModel::onAction,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCharacterScreen(
    state: CreateCharacterScreenState,
    onAction: (CreateCharacterScreenAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val avatarSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .widthIn(max = 330.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            AccessHeader(
                iconResourcePath = AccessIcons.Fingerprint,
                title = stringResource(Res.string.character_creation_title),
                subtitle = stringResource(Res.string.character_creation_subtitle),
            )

            SelectAvatarField(
                avatarUrlPath = state.selectedAvatar?.avatarImageUrl,
                onClick = { onAction(CreateCharacterScreenAction.OnUploadMugshotClick) },
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(Res.string.character_creation_name_label),
                    style =
                        AccessSubtitleTextStyle().copy(
                            color = AccessDefaults.FooterText
                        ),
                )

                BaseTextField(
                    state = state.detectiveNameState,
                    placeholder = stringResource(Res.string.character_creation_name_placeholder),
                    keyboardType = KeyboardType.Text,
                )
            }

            TraitAllocationAddCard(
                allocations = state.traitAllocations,
                remainingTraitPoints = state.remainingTraitPoints,
                baseTraitValue = state.baseTraitValue,
                onDecrease = { trait ->
                    onAction(CreateCharacterScreenAction.OnDecreaseTrait(trait))
                },
                onIncrease = { trait ->
                    onAction(CreateCharacterScreenAction.OnIncreaseTrait(trait))
                },
            )

            BaseButton(
                text = stringResource(Res.string.character_creation_primary_action_initialize_dossier),
                onClick = { onAction(CreateCharacterScreenAction.OnCreateCharacterClick) },
            )
        }
    }

    if (state.isAvatarArchiveVisible) {
        BaseBottomSheetContainer(
            title = stringResource(Res.string.avatar_sheet_title),
            footerText = stringResource(Res.string.avatar_sheet_footer_instruction),
            sheetState = avatarSheetState,
            onDismissRequest = { onAction(CreateCharacterScreenAction.OnDismissAvatarArchive) },
        ) {
            AvatarGrid(
                avatars = state.avatars.images,
                selectedAvatarId = state.selectedAvatarId,
                onAvatarSelected = { avatarId ->
                    onAction(CreateCharacterScreenAction.OnAvatarSelected(avatarId))
                },
            )
        }
    }
}

@Preview
@Composable
private fun CreateCharacterPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        CreateCharacterScreen(
            state = CreateCharacterScreenState(),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun CreateCharacterSheetPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        CreateCharacterScreen(
            state =
                CreateCharacterScreenState(
                    isAvatarArchiveVisible = true,
                    selectedAvatarId = "ID-2",
                ),
            onAction = {},
        )
    }
}
