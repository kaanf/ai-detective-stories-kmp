package com.kaanf.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFieldTextStyle

@Composable
fun BaseTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    BasicSurfaceField(
        state = state,
        placeholder = placeholder,
        modifier = modifier,
        interactionSource = interactionSource,
        keyboardType = keyboardType,
        minHeight = 46.dp,
        shape = null,
        borderColor =
            if (isFocused) {
                AccessDefaults.FieldFocusedBorder
            } else {
                AccessDefaults.FieldBorder
            },
        backgroundColor =
            if (isFocused) {
                AccessDefaults.FieldFocusedBackground
            } else {
                AccessDefaults.FieldBackground
            },
        textStyle = AccessFieldTextStyle(),
        placeholderColor = AccessDefaults.FieldPlaceholder,
        bottomAccentColor = if (isFocused) AccessDefaults.AlertLine else Color.Transparent,
        contentPadding = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    )
}

@Composable
private fun BasicSurfaceField(
    state: TextFieldState,
    placeholder: String,
    interactionSource: MutableInteractionSource,
    keyboardType: KeyboardType,
    minHeight: androidx.compose.ui.unit.Dp,
    borderColor: Color,
    backgroundColor: Color,
    textStyle: TextStyle,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: androidx.compose.ui.graphics.Shape? = null,
    bottomAccentColor: Color = Color.Transparent,
    contentPadding: Modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
) {
    val containerModifier = modifier.fillMaxWidth().heightIn(min = minHeight)

    Box(modifier = containerModifier) {
        Box(
            modifier =
                Modifier.matchParentSize().then(
                    if (shape != null) {
                        Modifier.background(color = backgroundColor, shape = shape)
                            .border(width = 1.dp, color = borderColor, shape = shape)
                    } else {
                        Modifier.background(color = backgroundColor)
                            .border(width = 1.dp, color = borderColor)
                    },
                ),
        ) {
            BasicTextField(
                state = state,
                enabled = enabled,
                interactionSource = interactionSource,
                textStyle = textStyle,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                lineLimits = TextFieldLineLimits.SingleLine,
                modifier = Modifier.fillMaxWidth(),
                decorator = { innerTextField ->
                    Box(
                        modifier = contentPadding.fillMaxWidth(),
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
                },
            )
        }

        if (bottomAccentColor != Color.Transparent) {
            Box(
                modifier =
                    Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                        .background(bottomAccentColor).height(1.dp),
            )
        }
    }
}
