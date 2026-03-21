package com.kaanf.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import detective_ai_stories.core.designsystem.generated.resources.Res
import detective_ai_stories.core.designsystem.generated.resources.inter_variable
import detective_ai_stories.core.designsystem.generated.resources.space_grotesk_bold
import detective_ai_stories.core.designsystem.generated.resources.special_elite_regular

val Inter @Composable get() =
    FontFamily(
        Font(
            resource = Res.font.inter_variable,
            weight = FontWeight.Light,
        ),
        Font(
            resource = Res.font.inter_variable,
            weight = FontWeight.Normal,
        ),
        Font(
            resource = Res.font.inter_variable,
            weight = FontWeight.Medium,
        ),
        Font(
            resource = Res.font.inter_variable,
            weight = FontWeight.SemiBold,
        ),
        Font(
            resource = Res.font.inter_variable,
            weight = FontWeight.Bold,
        ),
    )

val SpecialElite @Composable get() =
    FontFamily(
        Font(
            resource = Res.font.special_elite_regular,
            weight = FontWeight.Normal,
        ),
    )

val Typography @Composable get() =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                lineHeight = 14.sp,
            ),
    )
