package uz.mobidev.wallpaperapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import uz.mobidev.wallpaperapp.screens.HomeScreen
import uz.mobidev.wallpaperapp.ui.theme.WallpaperAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         WallpaperAppTheme {
            HomeScreen()
         }
      }
   }
}
