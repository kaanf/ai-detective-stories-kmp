package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import detective_ai_stories.core.designsystem.generated.resources.Res
import detective_ai_stories.core.designsystem.generated.resources.ic_snackbar_failure
import detective_ai_stories.core.designsystem.generated.resources.ic_snackbar_success
import detective_ai_stories.core.designsystem.generated.resources.ic_snackbar_warning
import detective_ai_stories.core.designsystem.generated.resources.snackbar_access_denied
import detective_ai_stories.core.designsystem.generated.resources.snackbar_uplink_failure
import detective_ai_stories.core.designsystem.generated.resources.snackbar_verification_complete
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class CustomSnackbarVariant(
    val titleRes: StringResource,
    val backgroundColor: Color,
    val borderColor: Color,
    val icon: DrawableResource,
) {
    Success(
        titleRes = Res.string.snackbar_verification_complete,
        backgroundColor = Color(0xFF0A1A0A),
        borderColor = Color(0xFF222222),
        icon = Res.drawable.ic_snackbar_success,
    ),
    Failure(
        titleRes = Res.string.snackbar_access_denied,
        backgroundColor = Color(0xFF1A0505),
        borderColor = Color(0xFF222222),
        icon = Res.drawable.ic_snackbar_failure,
    ),
    Warning(
        titleRes = Res.string.snackbar_uplink_failure,
        backgroundColor = Color(0xFF1A1500),
        borderColor = Color(0xFFD49D0C),
        icon = Res.drawable.ic_snackbar_warning,
    ),
}

private data class CustomSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val title: String? = null,
    val variant: CustomSnackbarVariant,
) : SnackbarVisuals

suspend fun SnackbarHostState.showSnackbar(
    message: String,
    variant: CustomSnackbarVariant,
    title: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short,
): SnackbarResult =
    showSnackbar(
        visuals =
            CustomSnackbarVisuals(
                message = message,
                duration = duration,
                title = title,
                variant = variant,
            ),
    )

@Composable
internal fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    val visuals = snackbarData.visuals as? CustomSnackbarVisuals
    val variant = visuals?.variant ?: CustomSnackbarVariant.Warning
    val title = visuals?.title ?: stringResource(variant.titleRes)

    Row(
        modifier =
            modifier
                .shadow(
                    elevation = 15.dp,
                    shape = RectangleShape,
                    clip = false,
                )
                .background(
                    color = variant.backgroundColor,
                    shape = RectangleShape,
                )
                .border(
                    width = 0.6.dp,
                    color = variant.borderColor,
                    shape = RectangleShape,
                )
                .padding(horizontal = 15.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(variant.icon),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 1.3.sp,
                            color = Color(0xFFE0E0E0),
                            textAlign = TextAlign.Start,
                            shadow =
                                Shadow(
                                    color = Color(0xFFE0E0E0).copy(alpha = 0.35f),
                                    blurRadius = 4f,
                                ),
                        ),
                )
            }

            Text(
                text = snackbarData.visuals.message,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style =
                    AccessMetaTextStyle().copy(
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = AccessDefaults.FieldText,
                        textAlign = TextAlign.Start,
                    ),
            )
        }
    }
}
