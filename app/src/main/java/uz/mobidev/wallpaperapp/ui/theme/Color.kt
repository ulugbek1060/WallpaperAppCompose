package uz.mobidev.wallpaperapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val ShimmerLightGrey = Color(0xFFF1F1F1)
val ShimmerMediumGrey = Color(0xFFE3E3E3)
val ShimmerDarkGrey = Color(0xFF1D1D1D)

val Colors.statusBarColor
   @Composable
   get() = if (isSystemInDarkTheme()) Color.Black else Purple700

val Colors.welcomeScreenBackgroundColor
   @Composable
   get() = if (isSystemInDarkTheme()) Color.Black else Color.White

val Colors.titleColor
   @Composable
   get() = if (isSystemInDarkTheme()) LightGray else DarkGray

val Colors.descriptionColor
   @Composable
   get() = if (isSystemInDarkTheme()) LightGray.copy(alpha = 0.5f) else DarkGray.copy(alpha = 0.5f)

val Colors.activeIndicatorColor
   @Composable
   get() = if (isSystemInDarkTheme()) Purple700 else Purple500

val Colors.inactiveIndicatorColor
   @Composable
   get() = if (isSystemInDarkTheme()) DarkGray else LightGray

val Colors.buttonBackgroundColor
   @Composable
   get() = if (isSystemInDarkTheme()) Purple700 else Purple500

val Colors.topAppBarContentColor
   @Composable
   get() = if (isSystemInDarkTheme()) LightGray else Color.White

val Colors.topAppBarBackgroundColor
   @Composable
   get() = if (isSystemInDarkTheme()) Color.Black else Purple500
