package uz.mobidev.wallpaperapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import uz.mobidev.wallpaperapp.navigation.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
   navController: NavHostController = rememberNavController(),
   viewModel: HomeViewModel = hiltViewModel()
) {
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text("Home")
            },
            actions = {
               IconButton(
                  onClick = {
                     navController.navigate(Screens.Search.route)
                  },
                  content = {
                     Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                     )
                  }
               )
            }
         )
      },
      content = {
         Box(
            modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
         ) {
            ListContent(
               images = viewModel.images.collectAsLazyPagingItems(),
               onItemClick = {
                  val encodedUrl = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                  navController.navigate(Screens.Detail.push(encodedUrl))
               }
            )
         }
      }
   )
}

