package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetContainer(
    title: String,
    footerText: String,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = onDismissRequest,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.84f),
        tonalElevation = 0.dp,
        shape = RectangleShape,
    ) {
        BoxWithConstraints(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            val maxSheetHeight = maxHeight * 0.8f

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                BottomSheetHandle(
                    modifier =
                        Modifier
                            .zIndex(1f),
                )

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .widthIn(max = 392.dp)
                            .heightIn(max = maxSheetHeight)
                            .padding(top = 12.dp)
                            .background(AccessDefaults.PanelBackground)
                            .border(1.dp, Color(0xFF333333)),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(69.dp)
                                .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = title,
                            style =
                                TextStyle(
                                    fontFamily = SpecialElite,
                                    fontSize = 10.sp,
                                    lineHeight = 15.sp,
                                    letterSpacing = 2.sp,
                                    color = AccessDefaults.HeadingColor,
                                ),
                        )

                        SheetCloseButton(
                            onClick = onCloseClick,
                        )
                    }

                    HorizontalDivider(
                        color = AccessDefaults.ButtonBorder,
                        thickness = 1.dp,
                    )

                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    brush =
                                        Brush.verticalGradient(
                                            colors =
                                                listOf(
                                                    AccessDefaults.PanelBackground,
                                                    Color(0xFF070707),
                                                    Color(0xFF050505),
                                                ),
                                        ),
                                )
                                .padding(24.dp),
                    ) {
                        content()
                    }

                    HorizontalDivider(
                        color = AccessDefaults.ButtonBorder,
                        thickness = 1.dp,
                    )

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(Color.Black)
                                .padding(horizontal = 28.dp, vertical = 13.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = footerText,
                            style =
                                TextStyle(
                                    fontFamily = SpecialElite,
                                    fontSize = 8.sp,
                                    lineHeight = 12.sp,
                                    letterSpacing = 0.8.sp,
                                    color = Color(0xFF444444),
                                ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomSheetHandle(
    modifier: Modifier = Modifier,
) {
    val borderColor = Color(0xFF333333)
    Row(
        modifier =
            modifier
                .background(AccessDefaults.ButtonBackground)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    // Top border
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth,
                    )
                    // Left border
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth,
                    )
                    // Right border
                    drawLine(
                        color = borderColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth,
                    )
                }
                .padding(horizontal = 38.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(3) {
            Box(
                modifier =
                    Modifier
                        .size(4.dp)
                        .background(
                            color = Color(0xFF333333),
                            shape = RectangleShape,
                        ),
            )
        }
    }
}

@Composable
private fun SheetCloseButton(
    onClick: () -> Unit,
) {
    Canvas(
        modifier =
            Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ),
    ) {
        val strokeWidth = 1.6.dp.toPx()
        val inset = size.width * 0.28f

        drawLine(
            color = AccessDefaults.FooterText,
            start = Offset(inset, inset),
            end = Offset(size.width - inset, size.height - inset),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = AccessDefaults.FooterText,
            start = Offset(size.width - inset, inset),
            end = Offset(inset, size.height - inset),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BaseBottomSheetContainerPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        BaseBottomSheetContainer(
            title = "GCPD VISUAL RECORDS ARCHIVE",
            footerText = "TERMINAL 409-B // SELECT VISUAL IDENTIFIER // SWIPE DOWN TO ABORT",
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = {},
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                repeat(4) { index ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                                .background(Color(0xFF111111))
                                .border(1.dp, if (index == 1) AccessDefaults.AlertLine else AccessDefaults.ButtonBorder),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "ARCHIVE SLOT ${index + 1}",
                            style =
                                TextStyle(
                                    fontFamily = SpecialElite,
                                    fontSize = 10.sp,
                                    lineHeight = 15.sp,
                                    letterSpacing = 1.2.sp,
                                    color = if (index == 1) AccessDefaults.AlertLine else AccessDefaults.HeadingColor,
                                ),
                        )
                    }
                }
            }
        }
    }
}
