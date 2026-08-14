package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val ToddlerLightColorScheme = lightColorScheme(
  primary = SkyBlue,
  onPrimary = TextDark,
  primaryContainer = SkyBlueLight,
  secondary = BrightOrange,
  onSecondary = TextDark,
  tertiary = CoralPink,
  background = CanvasBgLight,
  surface = CardBgLight,
  onBackground = TextDark,
  onSurface = TextDark
)

private val ToddlerDarkColorScheme = darkColorScheme(
  primary = SkyBlue,
  onPrimary = TextDark,
  primaryContainer = CardBgDark,
  secondary = BrightOrange,
  onSecondary = TextDark,
  tertiary = CoralPink,
  background = CanvasBgDark,
  surface = CardBgDark,
  onBackground = TextLight,
  onSurface = TextLight
)

val ToddlerShapes = Shapes(
  extraSmall = RoundedCornerShape(12.dp),
  small = RoundedCornerShape(18.dp),
  medium = RoundedCornerShape(24.dp),
  large = RoundedCornerShape(32.dp),
  extraLarge = RoundedCornerShape(40.dp)
)

@Composable
fun BabyWorldTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) ToddlerDarkColorScheme else ToddlerLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    shapes = ToddlerShapes,
    content = content
  )
}

