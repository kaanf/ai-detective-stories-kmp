package com.kaanf.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object AccessDefaults {
    val ScreenBackground = Color(0xFF1F1F1F)
    val PanelBackground = Color(0xFF0A0A0A)
    val HeadingColor = Color(0xFFE0E0E0)
    val SupportingText = Color(0xFF7A7A7A)
    val FooterText = Color(0xFF333333)
    val ButtonBackground = Color(0xFF121212)
    val ButtonBorder = Color(0xFF222222)
    val FieldBackground = Color(0xFF121212)
    val FieldFocusedBackground = Color(0xFF1A1A1A)
    val FieldBorder = Color(0xFF222222)
    val FieldFocusedBorder = Color(0xFF535353)
    val FieldText = Color(0xFFE0E0E0)
    val FieldPlaceholder = Color(0xFF4A4A4A)
    val AlertLine = Color(0xFF8A1C1C)
    val LoadingButtonText = Color(0xFFAAA4A4)
    val LoadingButtonBorder = Color(0xFF701616)
    val LoadingButtonBackgroundStart = Color(0xFF141010)
    val LoadingButtonBackgroundEnd = Color(0xFF1B1616)
}

@Suppress("ComposableNaming")
@Composable
fun AccessTitleTextStyle(
    fontSizeSp: Int,
    letterSpacingSp: Double,
) = TextStyle(
    fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Normal,
    fontSize = fontSizeSp.sp,
    lineHeight = 40.sp,
    letterSpacing = letterSpacingSp.sp,
    color = AccessDefaults.HeadingColor,
    textAlign = TextAlign.Center,
)

@Suppress("ComposableNaming")
@Composable
fun AccessLabelTextStyle() = TextStyle(
    fontFamily = SpecialElite,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 1.4.sp,
    color = AccessDefaults.SupportingText,
    textAlign = TextAlign.Center,
)

@Suppress("ComposableNaming")
@Composable
fun AccessButtonTextStyle() = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 1.4.sp,
    color = AccessDefaults.HeadingColor,
    textAlign = TextAlign.Center,
)

@Suppress("ComposableNaming")
@Composable
fun AccessSecondaryActionTextStyle() = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 1.2.sp,
    color = AccessDefaults.SupportingText,
    textAlign = TextAlign.Center,
)

@Suppress("ComposableNaming")
@Composable
fun AccessFieldTextStyle() = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = AccessDefaults.FieldText,
)

@Suppress("ComposableNaming")
@Composable
fun AccessFooterTextStyle() = TextStyle(
    fontFamily = Inter,
    fontSize = 12.sp,
    lineHeight = 15.sp,
    letterSpacing = 1.sp,
    color = AccessDefaults.SupportingText,
    textAlign = TextAlign.Center,
)
