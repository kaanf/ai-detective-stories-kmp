package com.kaanf.home.presentation.dispatch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dispatch_description
import detective_ai_stories.feature.home.presentation.generated.resources.dispatch_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val TopBarBackground = Color(0xCC0A0A0A)
private val TopBarBorder = Color(0xFF222222)
private val RadioBorder = Color(0x4D8A1C1C)
private val RadioBackground = Color(0xE60A0A0A)
private val DescBorderColor = Color(0x66333333)

@Composable
fun DispatchHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.dispatch_title),
            modifier = Modifier.fillMaxWidth(),
            style = AccessHeaderTextStyle().copy(
                fontSize = 26.sp
            ),
        )

        DispatchDescription()
    }
}

@Composable
private fun DispatchDescription() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0x4D222222),
                        Color.Transparent,
                    ),
                ),
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        DescBorderColor,
                        Color.Transparent,
                    ),
                ),
                shape = androidx.compose.ui.graphics.RectangleShape,
            )
            .padding(vertical = 16.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.dispatch_description),
            style = AccessMetaTextStyle().copy(
                fontSize = 12.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Preview
@Composable
private fun DispatchHeaderPreview() {
    DetectiveAiStoriesTheme {
        DispatchHeader()
    }
}
