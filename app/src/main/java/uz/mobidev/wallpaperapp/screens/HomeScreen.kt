package uz.mobidev.wallpaperapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.mobidev.wallpaperapp.R
import uz.mobidev.wallpaperapp.domain.models.WallpaperEntity


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
   navController: NavHostController = rememberNavController(),
   viewModel: HomeViewModel = hiltViewModel()
) {
   val state by viewModel.state.collectAsState()

   LaunchedEffect(key1 = true) {
      viewModel.loadItems()
   }

   Scaffold(
      topBar = { TopAppBar(title = { Text("Home") }) },
      content = {

         Box(
            modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
         ) {
            if (state.isLoading) {
               // Show loading indicator
               CircularProgressIndicator()
            } else if (state.error != null) {
               // Show error message
               Text(state.error!!)
            } else {
               // Show list of items
               LazyVerticalGrid(
                  columns = GridCells.Adaptive(minSize = 100.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
               ) {
                  items(state.wallpapers) {
                     ItemContent(item = it) {
                        // TODO: handle onCLick
                     }
                  }
               }
            }
         }
      }
   )
}

@Composable
fun ItemContent(item: WallpaperEntity, onCLick: () -> Unit) {
   AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
         .data(item.thumb)
         .crossfade(true)
         .build(),
      placeholder = painterResource(R.drawable.ic_launcher_background),
      contentDescription = stringResource(R.string.image),
      contentScale = ContentScale.Crop,
      modifier = Modifier
         .fillMaxWidth()
         .fillMaxHeight()
         .clip(RoundedCornerShape(12.dp)),
   )
}