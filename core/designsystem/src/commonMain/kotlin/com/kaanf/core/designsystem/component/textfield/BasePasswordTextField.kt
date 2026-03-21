package com.kaanf.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFieldTextStyle
import org.jetbrains.compose.resources.vectorResource
import detective_ai_stories.core.designsystem.generated.resources.Res
import detective_ai_stories.core.designsystem.generated.resources.ic_eye
import detective_ai_stories.core.designsystem.generated.resources.ic_eye_off

@Composable
fun BasePasswordTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    showVisibilityToggle: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    PasswordFieldSurface(
        state = state,
        placeholder = placeholder,
        modifier = modifier,
        minHeight = 46.dp,
        shape = null,
        interactionSource = interactionSource,
        borderColor = if (isFocused) AccessDefaults.FieldFocusedBorder else AccessDefaults.FieldBorder,
        backgroundColor = if (isFocused) AccessDefaults.FieldFocusedBackground else AccessDefaults.FieldBackground,
        textStyle = AccessFieldTextStyle(),
        placeholderColor = AccessDefaults.FieldPlaceholder,
        bottomAccentColor = if (isFocused) AccessDefaults.AlertLine else Color.Transparent,
        iconTint = AccessDefaults.FieldPlaceholder,
        showVisibilityToggle = showVisibilityToggle,
    )
}

@Composable
private fun PasswordFieldSurface(
    state: TextFieldState,
    placeholder: String,
    minHeight: androidx.compose.ui.unit.Dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    borderColor: Color,
    backgroundColor: Color,
    textStyle: TextStyle,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    bottomAccentColor: Color = Color.Transparent,
    iconTint: Color = AccessDefaults.FieldPlaceholder,
    showVisibilityToggle: Boolean = true,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val containerModifier = modifier
        .fillMaxWidth()
        .heightIn(min = minHeight)

    Box(modifier = containerModifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(
                    if (shape != null) {
                        Modifier
                            .background(color = backgroundColor, shape = shape)
                            .border(width = 1.dp, color = borderColor, shape = shape)
                    } else {
                        Modifier
                            .background(color = backgroundColor)
                            .border(width = 1.dp, color = borderColor)
                    },
                ),
        ) {
            BasicSecureTextField(
                state = state,
                enabled = enabled,
                interactionSource = interactionSource,
                textStyle = textStyle,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textObfuscationMode = if (isPasswordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
                modifier = Modifier.fillMaxWidth(),
                decorator = { innerTextField ->
                    if (showVisibilityToggle) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                if (state.text.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        color = placeholderColor,
                                        style = textStyle,
                                    )
                                }
                                innerTextField()
                            }

                            Icon(
                                imageVector = vectorResource(if (isPasswordVisible) Res.drawable.ic_eye_off else Res.drawable.ic_eye),
                                contentDescription = null,
                                tint = iconTint,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = { isPasswordVisible = !isPasswordVisible },
                                    ),
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = placeholderColor,
                                    style = textStyle,
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )
        }

        if (bottomAccentColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(bottomAccentColor)
                    .height(1.dp),
            )
        }
    }
}
