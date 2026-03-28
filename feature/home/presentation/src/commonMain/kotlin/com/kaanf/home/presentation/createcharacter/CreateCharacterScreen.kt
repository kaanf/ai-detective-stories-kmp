package com.kaanf.home.presentation.createcharacter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.BaseBottomSheetContainer
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.core.domain.home.UserAvatar
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.ic_fingerprint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private val DossierPanelWidth = 329.dp
private const val ScreenTitle = "PERSONNEL\nDOSSIER"
private const val ScreenSubtitle = "DEPARTMENT OF INVESTIGATIONS // CLASSIFIED"
private const val FooterCopy = "GCPD FORM 104-B // AUTHORIZED PERSONNEL ONLY"
private const val UploadLabel = "SUBJECT VISUAL RECORD"
private const val UploadCopy = "Upload Mugshot"
private const val NameLabel = "IDENTITY DESIGNATION"
private const val NamePlaceholder = "ENTER DETECTIVE NAME..."
private const val ProfileLabel = "PSYCHOLOGICAL PROFILE"
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
                DossierHeader()

                UploadMugshotSection(
                    selectedAvatar = state.selectedAvatar,
                    onClick = { onAction(CreateCharacterScreenAction.OnUploadMugshotClick) },
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SectionLabel(
                        text = NameLabel,
                    )

                    DossierNameField(
                        state = state.detectiveNameState,
                    )
                }

                PsychologicalProfileCard(
                    allocations = state.traitAllocations,
                    remainingTraitPoints = state.remainingTraitPoints,
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

            Text(
                text = FooterCopy,
                style =
                    dossierMetaTextStyle(
                        color = AccessDefaults.FooterText.copy(alpha = 0.6f),
                    ),
                textAlign = TextAlign.Center,
            )
        }

        if (state.isAvatarArchiveVisible) {
            BaseBottomSheetContainer(
                title = AvatarArchiveTitle,
                footerText = AvatarArchiveFooter,
                sheetState = avatarSheetState,
                onDismissRequest = { onAction(CreateCharacterScreenAction.OnDismissAvatarArchive) },
            ) {
                AvatarArchiveGrid(
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
private fun DossierHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_fingerprint),
            contentDescription = null,
            tint = AccessDefaults.FooterText
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = ScreenTitle,
            style = dossierTitleTextStyle(),
            textAlign = TextAlign.Center,
        )

        Text(
            text = ScreenSubtitle,
            style = dossierMetaTextStyle(),
            textAlign = TextAlign.Center,
        )

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            color = AccessDefaults.ButtonBorder,
            thickness = 1.dp,
        )
    }
}

@Composable
private fun UploadMugshotSection(
    selectedAvatar: UserAvatar?,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionLabel(
            text = UploadLabel,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier =
                Modifier
                    .size(128.dp)
                    .background(AccessDefaults.ButtonBackground)
                    .border(1.dp, AccessDefaults.ButtonBorder, RectangleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick,
                    ),
        ) {
            if (selectedAvatar == null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    UploadIcon()

                    Text(
                        text = UploadCopy,
                        style =
                            dossierMetaTextStyle(
                                fontSize = 9.sp,
                                lineHeight = 13.5.sp,
                                letterSpacing = 0.sp,
                                color = AccessDefaults.SupportingText,
                            ),
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                AsyncImage(
                    model = selectedAvatar.avatarImageUrl,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun AvatarArchiveGrid(
    avatars: List<UserAvatar>,
    selectedAvatarId: String?,
    onAvatarSelected: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(max = 584.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(
            items = avatars,
            key = { avatar -> avatar.id },
        ) { avatar ->
            AvatarArchiveCard(
                avatar = avatar,
                isSelected = avatar.id == selectedAvatarId,
                onClick = { onAvatarSelected(avatar.id) },
            )
        }
    }
}

@Composable
private fun AvatarArchiveCard(
    avatar: UserAvatar,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(184.dp)
                .background(Color(0xFF111111))
                .border(
                    width = 1.dp,
                    color = if (isSelected) AccessDefaults.AlertLine else AccessDefaults.ButtonBorder,
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            AsyncImage(
                model = avatar.avatarImageUrl,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
private fun DossierNameField(
    state: TextFieldState,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(54.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(if (isFocused) AccessDefaults.FieldFocusedBackground else AccessDefaults.FieldBackground)
                    .border(
                        width = 1.dp,
                        color = if (isFocused) AccessDefaults.FieldFocusedBorder else AccessDefaults.FieldBorder,
                    ),
        ) {
            BasicTextField(
                state = state,
                interactionSource = interactionSource,
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                    ),
                textStyle = dossierNameTextStyle().copy(color = AccessDefaults.HeadingColor),
                modifier = Modifier.fillMaxWidth(),
                decorator = { innerTextField ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (state.text.isEmpty()) {
                            Text(
                                text = NamePlaceholder,
                                style = dossierNameTextStyle().copy(color = AccessDefaults.FieldPlaceholder),
                            )
                        }

                        innerTextField()
                    }
                },
            )
        }

        Box(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AccessDefaults.AlertLine),
        )
    }
}

@Composable
private fun PsychologicalProfileCard(
    allocations: List<TraitAllocation>,
    remainingTraitPoints: Int,
    onDecrease: (CharacterTrait) -> Unit,
    onIncrease: (CharacterTrait) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(AccessDefaults.ButtonBackground)
                .border(1.dp, AccessDefaults.ButtonBorder),
    ) {
        Box(
            modifier =
                Modifier
                    .width(4.dp)
                    .height(292.dp)
                    .background(AccessDefaults.FooterText.copy(alpha = 0.7f)),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = ProfileLabel,
                    style = dossierMetaTextStyle(color = AccessDefaults.SupportingText),
                )

                Text(
                    text = "UNALLOCATED TRAITS: $remainingTraitPoints",
                    style =
                        dossierMetaTextStyle(
                            color = AccessDefaults.AlertLine,
                        ),
                )
            }

            HorizontalDivider(
                color = AccessDefaults.ButtonBorder,
                thickness = 1.dp,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                allocations.forEach { allocation ->
                    TraitAllocationRow(
                        allocation = allocation,
                        canDecrease = allocation.value > CreateCharacterScreenState.BaseTraitValue,
                        canIncrease = remainingTraitPoints > 0,
                        onDecrease = { onDecrease(allocation.trait) },
                        onIncrease = { onIncrease(allocation.trait) },
                    )
                }
            }
        }
    }
}

@Composable
private fun TraitAllocationRow(
    allocation: TraitAllocation,
    canDecrease: Boolean,
    canIncrease: Boolean,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = allocation.trait.title,
                style = dossierTraitTitleTextStyle(),
            )

            Text(
                text = allocation.trait.description.uppercase(),
                style = dossierTraitDescriptionTextStyle(),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TraitControlButton(
                symbol = "−",
                enabled = canDecrease,
                onClick = onDecrease,
            )

            Text(
                text = allocation.value.toString(),
                modifier = Modifier.width(32.dp),
                style = dossierValueTextStyle(),
                textAlign = TextAlign.Center,
            )

            TraitControlButton(
                symbol = "+",
                enabled = canIncrease,
                onClick = onIncrease,
            )
        }
    }
}

@Composable
private fun TraitControlButton(
    symbol: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(24.dp)
                .alpha(if (enabled) 1f else 0.45f)
                .border(1.dp, AccessDefaults.FooterText.copy(alpha = 0.55f))
                .clickable(
                    enabled = enabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = symbol,
            style =
                TextStyle(
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = AccessDefaults.HeadingColor,
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Composable
private fun UploadIcon() {
    val strokeColor = AccessDefaults.FooterText.copy(alpha = 0.8f)

    Canvas(
        modifier = Modifier.size(24.dp),
    ) {
        val strokeWidth = 1.4.dp.toPx()
        val centerX = size.width / 2f
        val topY = size.height * 0.2f
        val trayTop = size.height * 0.68f
        val trayInset = size.width * 0.24f

        drawLine(
            color = strokeColor,
            start = Offset(centerX, size.height * 0.7f),
            end = Offset(centerX, topY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = strokeColor,
            start = Offset(centerX, topY),
            end = Offset(centerX - size.width * 0.18f, topY + size.height * 0.18f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = strokeColor,
            start = Offset(centerX, topY),
            end = Offset(centerX + size.width * 0.18f, topY + size.height * 0.18f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        val trayPath =
            Path().apply {
                moveTo(trayInset, trayTop)
                lineTo(trayInset, size.height * 0.82f)
                lineTo(size.width - trayInset, size.height * 0.82f)
                lineTo(size.width - trayInset, trayTop)
            }

        drawPath(
            path = trayPath,
            color = strokeColor,
            style = Stroke(width = strokeWidth),
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
private fun dossierTitleTextStyle() =
    TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 4.8.sp,
        color = AccessDefaults.HeadingColor,
        textAlign = TextAlign.Center,
    )

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

@Composable
private fun dossierNameTextStyle() =
    TextStyle(
        fontFamily = SpecialElite,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.9.sp,
    )

@Composable
private fun dossierTraitTitleTextStyle() =
    TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.7.sp,
        color = AccessDefaults.HeadingColor,
    )

@Composable
private fun dossierTraitDescriptionTextStyle() =
    TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp,
        lineHeight = 13.5.sp,
        color = AccessDefaults.FooterText,
    )

@Composable
private fun dossierValueTextStyle() =
    TextStyle(
        fontFamily = SpecialElite,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = AccessDefaults.HeadingColor,
    )
