package com.softpie.karabiner.component.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/

@Composable
fun KarabinerTheme(
    typography: KarabinerTypography = KarabinerTheme.typography,
    color: KarabinerColor = KarabinerTheme.color,
    shape: KarabinerShape = KarabinerTheme.shape,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColor provides color,
        LocalTypography provides typography,
        LocalShape provides shape
    ) {
        content()
    }
}

object KarabinerTheme {
    val color: KarabinerColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current
    val typography: KarabinerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
    val shape: KarabinerShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current
}

//@Composable
//fun KarabinerTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}