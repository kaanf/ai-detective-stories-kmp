package com.kaanf.character.presentation.createcharacter.component.trait

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.Inter

enum class TraitControlButtonState {
    Increase, Decrease
}

@Composable
fun TraitControlButton(
    state: TraitControlButtonState,
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
            text = if (state == TraitControlButtonState.Increase) "+" else "-",
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
