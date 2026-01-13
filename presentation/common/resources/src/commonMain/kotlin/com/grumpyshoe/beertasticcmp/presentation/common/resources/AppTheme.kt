package com.grumpyshoe.beertasticcmp.presentation.common.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object AppTheme {
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors =
        if (darkTheme) darkColors else lightColors

    val typography = rememberAppTypography()

    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalColors provides colors,
    ) {

        MaterialTheme(
            colorScheme = colors.material,
            typography = typography.material,
            content = content,
        )
    }
}
