package com.kaanf.character.presentation.createcharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.character.presentation.createcharacter.component.avatar.AvatarGrid
import com.kaanf.character.presentation.createcharacter.component.trait.TraitAllocationAddCard
import com.kaanf.character.presentation.createcharacter.component.avatar.SelectAvatarField
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.BaseBottomSheetContainer
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.textfield.BaseTextField
import com.kaanf.core.designsystem.component.title.AccessHeader
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.core.presentation.util.TestTags
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private val DossierPanelWidth = 329.dp
private const val NameLabel = "IDENTITY DESIGNATION"
private const val EnterCityCopy = "SIGN DOSSIER & ENTER CITY"
private const val AvatarArchiveTitle = "GCPD VISUAL RECORDS ARCHIVE"
private const val AvatarArchiveFooter = "TERMINAL 409-B // SELECT VISUAL IDENTIFIER // SWIPE DOWN TO ABORT"

@Composable
fun CreateCharacterRoot(
    viewModel: CreateCharacterScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarScaffold(snackbarHostState) {
        CreateCharacterScreenScreen(
            state = state,
            onAction = viewModel::onAction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCharacterScreenScreen(
    state: CreateCharacterScreenState,
    onAction: (CreateCharacterScreenAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val avatarSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .testTag(TestTags.CHARACTER_SCREEN)
                .background(AccessDefaults.PanelBackground),
    ) {
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
                        .widthIn(max = DossierPanelWidth),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                AccessHeader(
                    iconResourcePath = AccessIcons.Fingerprint,
                    title = "PERSONAL\nDOSSIER",
                    subtitle = "DEPARTMENT OF INVESTIGATIONS",
                )

                SelectAvatarField(
                    avatarUrlPath = state.selectedAvatar?.avatarImageUrl,
                    onClick = { onAction(CreateCharacterScreenAction.OnUploadMugshotClick) },
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SectionLabel(
                        text = NameLabel,
                    )

                    BaseTextField(
                        state = state.detectiveNameState,
                        placeholder = "Enter Detective Name",
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
                    text = EnterCityCopy,
                    onClick = { onAction(CreateCharacterScreenAction.OnCreateCharacterClick) },
                    enabled = state.isSubmitEnabled,
                )
            }
        }

        if (state.isAvatarArchiveVisible) {
            BaseBottomSheetContainer(
                title = AvatarArchiveTitle,
                footerText = AvatarArchiveFooter,
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
}

@Preview
@Composable
private fun CreateCharacterPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        CreateCharacterScreenScreen(
            state = CreateCharacterScreenState(),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun CreateCharacterSheetPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        CreateCharacterScreenScreen(
            state =
                CreateCharacterScreenState(
                    isAvatarArchiveVisible = true,
                    selectedAvatarId = "ID-2",
                ),
            onAction = {},
        )
    }
}

@Composable
private fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        modifier = modifier,
        style =
            dossierMetaTextStyle(
                color = AccessDefaults.FooterText,
            ),
        textAlign = textAlign,
    )
}
@Composable
private fun dossierMetaTextStyle(
    color: Color = AccessDefaults.SupportingText,
    fontSize: TextUnit = 10.sp,
    lineHeight: TextUnit = 15.sp,
    letterSpacing: TextUnit = 1.sp,
) = TextStyle(
    fontFamily = SpecialElite,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    color = color,
)
