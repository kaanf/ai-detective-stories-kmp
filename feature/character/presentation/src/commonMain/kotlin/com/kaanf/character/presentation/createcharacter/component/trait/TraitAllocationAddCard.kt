package com.kaanf.character.presentation.createcharacter.component.trait

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.character.domain.model.trait.CharacterTrait
import com.kaanf.character.domain.model.trait.TraitAllocation
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle

@Composable
fun TraitAllocationAddCard(
    allocations: List<TraitAllocation>,
    remainingTraitPoints: Int,
    baseTraitValue: Int,
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
                    .wrapContentHeight()
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
                    text = "ProfileLabel",
                    style = AccessMetaTextStyle(),
                )

                Text(
                    text = "TRAIT POINT: $remainingTraitPoints",
                    style = AccessMetaTextStyle().copy(
                        color = AccessDefaults.AlertLine
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
                        canDecrease = allocation.value > baseTraitValue,
                        canIncrease = remainingTraitPoints > 0,
                        onDecrease = { onDecrease(allocation.trait) },
                        onIncrease = { onIncrease(allocation.trait) },
                    )
                }
            }
        }
    }
}
