package com.kaanf.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.home.domain.model.CaseDifficulty
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.case_difficulty_label
import detective_ai_stories.feature.home.presentation.generated.resources.case_placeholder_description
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_encrypted
import detective_ai_stories.feature.home.presentation.generated.resources.ic_difficulty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val BodyTitleColor = Color(0xFFEBE5D6)
private val BodyDescriptionColor = Color(0xFF777777)
private val BodyMetadataColor = Color(0xFF444444)
private val EncryptedTextColor = Color(0xFF8A1C1C)
private val EncryptedBackground = Color(0xE6000000)

@Composable
fun CaseCardBodyContent(
    title: String,
    metadata: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title.uppercase(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = AccessHeaderTextStyle().copy(
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 2.4.sp,
                color = BodyTitleColor,
                textAlign = TextAlign.Start,
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.case_placeholder_description),
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .align(Alignment.TopStart)
                    .alpha(0.5f)
                    .blur(4.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontFamily = Inter,
                    fontSize = 12.sp,
                    lineHeight = 19.5.sp,
                    color = BodyDescriptionColor,
                    textAlign = TextAlign.Start,
                ),
            )

            Box(
                modifier = Modifier
                    .background(EncryptedBackground)
                    .border(1.dp, AccessDefaults.FingerprintIndicatorFrame)
                    .padding(horizontal = 12.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(Res.string.dashboard_case_encrypted),
                    style = TextStyle(
                        fontFamily = Inter,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        letterSpacing = 2.sp,
                        color = EncryptedTextColor,
                    ),
                )
            }
        }

        Text(
            text = metadata.uppercase(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontFamily = Inter,
                fontSize = 9.sp,
                lineHeight = 13.5.sp,
                letterSpacing = 1.8.sp,
                color = BodyMetadataColor,
            ),
        )
    }
}

@Composable
fun CaseDifficultyIndicator(
    difficulty: CaseDifficulty,
    modifier: Modifier = Modifier,
) {
    val filledCount = when (difficulty) {
        CaseDifficulty.EASY -> 1
        CaseDifficulty.MEDIUM -> 2
        CaseDifficulty.HARD -> 3
    }
    val filledColor = when (difficulty) {
        CaseDifficulty.EASY -> AccessDefaults.SuccessLine
        CaseDifficulty.MEDIUM -> AccessDefaults.GoldIconBackground
        CaseDifficulty.HARD -> AccessDefaults.AlertLine
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.case_difficulty_label),
            style = TextStyle(
                fontFamily = Inter,
                fontSize = 10.sp,
                lineHeight = 13.5.sp,
                letterSpacing = 0.9.sp,
                color = AccessDefaults.FooterText,
                textAlign = TextAlign.End,
            ),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            repeat(3) { index ->
                Icon(
                    painter = painterResource(Res.drawable.ic_difficulty),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (index < filledCount) filledColor else AccessDefaults.FooterText,
                )
            }
        }
    }
}
