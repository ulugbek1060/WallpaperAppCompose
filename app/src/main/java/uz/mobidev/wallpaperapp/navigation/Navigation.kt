package uz.mobidev.wallpaperapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.mobidev.wallpaperapp.screens.DetailScreen
import uz.mobidev.wallpaperapp.screens.HomeScreen
import uz.mobidev.wallpaperapp.screens.SearchScreen

const val IMAGE_FULL_URL = "image_full_url"

sealed class Screens(val route: String) {
   object Home : Screens("home_screen")
   object Search : Screens("search_screen")
   object Detail : Screens("detail_screen/{$IMAGE_FULL_URL}") {
      fun push(url: String): String {
         return "detail_screen/$url"
      }
   }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
   NavHost(
      navController = navController,
      startDestination = Screens.Home.route
   ) {
      composable(
         route = Screens.Home.route,
         content = { HomeScreen(navController = navController) }
      )
      composable(
         route = Screens.Search.route,
         content = { SearchScreen(navController = navController) }
      )
      composable(
         route = Screens.Detail.route,
         content = { DetailScreen() },
         arguments = listOf(
            navArgument(
               name = IMAGE_FULL_URL,
               builder = {
                  type = NavType.StringType
               }
            )
         )
      )
   }
}