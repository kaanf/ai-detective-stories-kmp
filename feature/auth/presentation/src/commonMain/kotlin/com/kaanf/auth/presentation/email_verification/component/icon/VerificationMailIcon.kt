package com.kaanf.auth.presentation.email_verification.component.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.ic_mail_red
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun VerificationMailIcon() {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            painter = painterResource(Res.drawable.ic_mail_red),
            tint = AccessDefaults.AlertLine,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun VerificationMailIconPreview() {
    VerificationMailIcon()
}
