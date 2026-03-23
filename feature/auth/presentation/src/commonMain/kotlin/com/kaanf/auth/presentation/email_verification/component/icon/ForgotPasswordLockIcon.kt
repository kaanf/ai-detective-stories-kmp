package com.kaanf.auth.presentation.email_verification.component.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.White
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.ic_lock
import org.jetbrains.compose.resources.painterResource

@Composable
fun ForgotPasswordLockIcon(
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter = ColorFilter.tint(White),
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = modifier.size(128.dp),
            painter = painterResource(Res.drawable.ic_lock),
            colorFilter = colorFilter,
            contentDescription = null,
        )
    }
}
