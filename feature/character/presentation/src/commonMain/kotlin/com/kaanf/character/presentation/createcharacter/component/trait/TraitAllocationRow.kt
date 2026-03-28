package com.kaanf.character.presentation.createcharacter.component.trait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.character.domain.model.trait.TraitAllocation
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TraitAllocationRow(
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
                style = AccessHeaderTextStyle().copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.7.sp,
                ),
            )

            Text(
                text = allocation.trait.description.uppercase(),
                style = AccessMetaTextStyle().copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    lineHeight = 13.5.sp,
                ),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TraitControlButton(
                state = TraitControlButtonState.Decrease,
                enabled = canDecrease,
                onClick = onDecrease,
            )

            Text(
                text = allocation.value.toString(),
                modifier = Modifier.width(32.dp),
                style = AccessSubtitleTextStyle().copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = AccessDefaults.HeadingColor,
                ),
                textAlign = TextAlign.Center,
            )

            TraitControlButton(
                state = TraitControlButtonState.Increase,
                enabled = canIncrease,
                onClick = onIncrease,
            )
        }
    }
}

@Composable
@Preview
private fun TraitAllocationRowPreview() {
    DetectiveAiStoriesTheme {
    }
}
