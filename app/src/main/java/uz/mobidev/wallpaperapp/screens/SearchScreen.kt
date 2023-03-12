package uz.mobidev.wallpaperapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.mobidev.wallpaperapp.navigation.Screens
import uz.mobidev.wallpaperapp.ui.theme.TOP_APP_BAR_HEIGHT
import uz.mobidev.wallpaperapp.ui.theme.statusBarColor
import uz.mobidev.wallpaperapp.ui.theme.topAppBarBackgroundColor
import uz.mobidev.wallpaperapp.ui.theme.topAppBarContentColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
   navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()
) {
   val query by viewModel.searchQuery

   val systemUiController = rememberSystemUiController()
   systemUiController.setStatusBarColor(
      color = MaterialTheme.colors.statusBarColor
   )
   Scaffold(topBar = {
      SearchTopBar(text = query, onTextChange = {
         viewModel.updateSearchQuery(it)
      }, onSearchClicked = {
         viewModel.searchImages(it)
      }, onCloseClicked = {
         navController.popBackStack()
      })
   }, content = {
      ListContent(
         images = viewModel.searchImages.collectAsLazyPagingItems(),
         onItemClick = {
            val encodedUrl = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
            navController.navigate(Screens.Detail.push(encodedUrl))
         }
      )
   })
}

@Composable
fun SearchTopBar(
   text: String,
   onTextChange: (String) -> Unit,
   onSearchClicked: (String) -> Unit,
   onCloseClicked: () -> Unit
) {
   SearchWidget(
      text = text,
      onTextChange = onTextChange,
      onSearchClicked = onSearchClicked,
      onCloseClicked = onCloseClicked
   )
}

@Composable
fun SearchWidget(
   text: String,
   onTextChange: (String) -> Unit,
   onSearchClicked: (String) -> Unit,
   onCloseClicked: () -> Unit
) {
   Surface(
      modifier = Modifier
         .fillMaxWidth()
         .height(TOP_APP_BAR_HEIGHT),
      elevation = AppBarDefaults.BottomAppBarElevation,
      color = MaterialTheme.colors.topAppBarBackgroundColor
   ) {
      TextField(
         modifier = Modifier.fillMaxWidth(),
         value = text,
         onValueChange = { onTextChange(it) },
         placeholder = {
            Text(
               modifier = Modifier.alpha(ContentAlpha.medium),
               text = "Search...",
               color = Color.White
            )
         },
         textStyle = TextStyle(
            color = MaterialTheme.colors.topAppBarContentColor
         ),
         singleLine = true,
         leadingIcon = {
            IconButton(
               onClick = { }, modifier = Modifier.alpha(alpha = ContentAlpha.medium)
            ) {
               Icon(
                  imageVector = Icons.Default.Search,
                  contentDescription = "Search",
                  tint = MaterialTheme.colors.topAppBarContentColor
               )
            }
         },
         trailingIcon = {
            IconButton(
               onClick = {
                  if (text.isNotEmpty()) {
                     onTextChange("")
                  } else {
                     onCloseClicked()
                  }
               },
            ) {
               Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Close",
                  tint = MaterialTheme.colors.topAppBarContentColor
               )
            }
         },
         keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
         ),
         keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(text)
         }),
         colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.topAppBarContentColor
         )
      )
   }
}
