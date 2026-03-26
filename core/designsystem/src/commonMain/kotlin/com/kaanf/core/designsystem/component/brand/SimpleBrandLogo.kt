package com.kaanf.core.designsystem.component.brand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessTitleTextStyle

@Composable
fun SimpleBrandLogo(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style =
                AccessTitleTextStyle(
                    fontSizeSp = 40,
                    letterSpacingSp = 7.2,
                ),
        )

        Text(
            text = subtitle,
            style =
                AccessTitleTextStyle(
                    fontSizeSp = 20,
                    letterSpacingSp = 4.2,
                ),
        )
    }
}
