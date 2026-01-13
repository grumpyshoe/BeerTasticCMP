package com.grumpyshoe.beertasticcmp.presentation.common.resources

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun rememberRobotoFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.roboto_regular, FontWeight.Normal),
        Font(Res.font.roboto_medium, FontWeight.Medium),
        Font(Res.font.roboto_bold, FontWeight.SemiBold),
        Font(Res.font.roboto_bold, FontWeight.Bold),
    )
}

@Composable
fun rememberAppTypography(): AppTypography {
    val robotoFont = rememberRobotoFontFamily()
    val density = LocalDensity.current

    return remember(robotoFont, density) {
        AppTypography(
            material = Typography(
                displayLarge =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 57.sp,
                        lineHeight = 64.sp,
                    ),
                displayMedium =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 45.sp,
                        lineHeight = 52.sp,
                    ),
                displaySmall =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 36.sp,
                        lineHeight = 44.sp,
                    ),
                headlineLarge =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                    ),
                headlineMedium =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 28.sp,
                        lineHeight = 36.sp,
                    ),
                headlineSmall =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                    ),
                titleLarge =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                    ),
                titleMedium =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                    ),
                titleSmall =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                    ),
                labelLarge =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                    ),
                labelMedium =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                    ),
                labelSmall =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                    ),
                bodyLarge =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                    ),
                bodyMedium =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.25.sp,
                    ),
                bodySmall =
                    TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.4.sp,
                    ),
            ),
            customFont = TextStyle(
                fontFamily = robotoFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 45.sp,
            )
        )
    }
}

class AppTypography(val material: Typography, val customFont: TextStyle)

val LocalTypography = staticCompositionLocalOf<AppTypography> {
    error("Typography not provided")
}
