package com.kaanf.auth.presentation.email_verification.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle

@Composable
fun VerificationInfoPanel(
    title: String,
    description: String,
    color: Color
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(AccessDefaults.ButtonBackground)
                .border(
                    width = 1.dp,
                    color = AccessDefaults.ButtonBorder,
                ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            Box(
                modifier =
                    Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(color.copy(alpha = 0.5f)),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    text = title,
                    style = AccessLabelTextStyle().copy(
                        letterSpacing = 0.sp,
                        lineHeight = 22.8.sp
                    ),
                )

                Text(
                    text = description,
                    style = AccessLabelTextStyle().copy(
                        fontSize = 12.sp,
                        lineHeight = 19.5.sp,
                        letterSpacing = 1.2.sp,
                        color = Color(0xFF555555)
                    ),
                )
            }
        }
    }
}
