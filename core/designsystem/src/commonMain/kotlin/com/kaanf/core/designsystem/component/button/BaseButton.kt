package com.kaanf.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessButtonTextStyle

@Composable
fun BaseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    loadingText: String = "AUTHENTICATING...",
) {
    val backgroundModifier =
        if (isLoading) {
            Modifier.background(
                brush =
                    Brush.horizontalGradient(
                        colors =
                            listOf(
                                AccessDefaults.LoadingButtonBackgroundStart,
                                AccessDefaults.LoadingButtonBackgroundEnd,
                                AccessDefaults.LoadingButtonBackgroundStart,
                            ),
                    ),
            )
        } else {
            Modifier.background(AccessDefaults.ButtonBackground)
        }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .alpha(if (enabled) 1f else 0.6f)
            .then(backgroundModifier)
            .border(
                width = 1.dp,
                color = if (isLoading) AccessDefaults.LoadingButtonBorder else AccessDefaults.ButtonBorder,
            )
            .clickable(
                enabled = enabled && !isLoading,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            Text(
                text = loadingText,
                style =
                    AccessButtonTextStyle().copy(
                        color = AccessDefaults.LoadingButtonText,
                        letterSpacing = 2.2.sp,
                    ),
            )
        } else {
            Text(
                text = text,
                style = AccessButtonTextStyle(),
            )
        }
    }
}
