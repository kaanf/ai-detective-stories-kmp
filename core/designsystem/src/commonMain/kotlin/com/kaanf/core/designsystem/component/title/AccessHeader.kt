package com.kaanf.core.designsystem.component.title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AccessHeader(
    iconResourcePath: DrawableResource? = null,
    title: String = "",
    subtitle: String = ""
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        iconResourcePath?.let {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 12.dp),
                painter = painterResource(it),
                contentDescription = null,
                tint = AccessDefaults.FooterText
            )
        }

        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = AccessHeaderTextStyle(),
            )
        }

        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                style = AccessSubtitleTextStyle()
            )
        }
    }
}

@Preview
@Composable
fun AccessHeaderPreview() {
    DetectiveAiStoriesTheme {
        AccessHeader(
            iconResourcePath = AccessIcons.Fingerprint,
            title = "Personal Dossier".uppercase(),
            subtitle = "Department of Investigations // Classified"
        )
    }
}
