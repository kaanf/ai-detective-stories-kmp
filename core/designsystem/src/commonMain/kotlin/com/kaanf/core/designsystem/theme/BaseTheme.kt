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
    val PanelAlternativeBackground = Color(0xFF111111)
    val LoadingScreenBackground = Color(0xFF050303)
    val LoadingOverlayScrim = Color(0xD6050505)
    val QuoteBackground = Color(0xFF050505)
    val HeadingColor = Color(0xFFE0E0E0)
    val SupportingText = Color(0xFF7A7A7A)
    val FooterText = Color(0xFF555555)
    val ButtonBackground = Color(0xFF121212)
    val ButtonBorder = Color(0xFF222222)
    val FieldBackground = Color(0xFF121212)
    val FieldFocusedBackground = Color(0xFF1A1A1A)
    val FieldBorder = Color(0xFF222222)
    val FieldFocusedBorder = Color(0xFF535353)
    val FieldText = Color(0xFFE0E0E0)
    val FieldPlaceholder = Color(0xFF4A4A4A)
    val AlertLine = Color(0xFF8A1C1C)
    val QuoteAccent = Color(0x808A1C1C)
    val QuoteTextColor = Color(0xFF888888)
    val SuccessLine = Color(0xFF5A8A1C)
    val LoadingButtonText = Color(0xFFAAA4A4)
    val LoadingButtonBorder = Color(0xFF701616)
    val LoadingButtonBackgroundStart = Color(0xFF141010)
    val LoadingButtonBackgroundEnd = Color(0xFF1B1616)
    val LoadingIndicatorOuterTrack = Color(0xFF222222)
    val FingerprintIndicatorIdle = Color(0xFF555555)
    val FingerprintIndicatorActive = Color(0xFFF6F2E9)
    val FingerprintIndicatorGlow = Color(0xCCEBE5D6)
    val FingerprintIndicatorFrame = Color(0xFF333333)
    val EnergyIconBackground = Color(0xFF8A1C1C)
    val GoldIconBackground = Color(0xFFD49D0C)
    val CardBackground = Color(0xCC0A0A0A)
    val TimerBorder = Color(0x668A1C1C)
    val ButtonTextColor = Color(0xFFA0A0A0)
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
fun AccessLabelTextStyle() =
    TextStyle(
        fontFamily = SpecialElite,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.4.sp,
        color = AccessDefaults.SupportingText,
        textAlign = TextAlign.Center,
    )

@Suppress("ComposableNaming")
@Composable
fun AccessButtonTextStyle() =
    TextStyle(
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
fun AccessSecondaryActionTextStyle() =
    TextStyle(
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
fun AccessFieldTextStyle() =
    TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = AccessDefaults.FieldText,
    )

@Suppress("ComposableNaming")
@Composable
fun AccessFooterTextStyle() =
    TextStyle(
        fontFamily = Inter,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        letterSpacing = 1.sp,
        color = AccessDefaults.SupportingText,
        textAlign = TextAlign.Center,
    )

// region
@Suppress("ComposableNaming")
@Composable
fun AccessHeaderTextStyle() =
    TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 4.7.sp,
        color = AccessDefaults.HeadingColor,
        textAlign = TextAlign.Center,
    )

@Suppress("ComposableNaming")
@Composable
fun AccessSubtitleTextStyle() =
    TextStyle(
        fontFamily = SpecialElite,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        letterSpacing = 1.4.sp,
        color = AccessDefaults.SupportingText,
        textAlign = TextAlign.Center,
    )

@Suppress("ComposableNaming")
@Composable
fun AccessMetaTextStyle() =
    TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 20.sp,
        color = AccessDefaults.SupportingText,
    )
// endregion
