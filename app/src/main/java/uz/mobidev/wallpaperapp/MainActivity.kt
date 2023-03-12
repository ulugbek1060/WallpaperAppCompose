package uz.mobidev.wallpaperapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.mobidev.wallpaperapp.navigation.SetupNavGraph
import uz.mobidev.wallpaperapp.ui.theme.WallpaperAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   private lateinit var navController: NavHostController

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         WallpaperAppTheme {
            navController = rememberNavController()
            SetupNavGraph(navController = navController)
         }
      }
   }
}
