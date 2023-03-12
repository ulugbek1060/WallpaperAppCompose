package uz.mobidev.wallpaperapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.mobidev.wallpaperapp.R

@Composable
fun DetailScreen(
   viewModel: DetailViewModel = hiltViewModel()
) {
   Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
   ) {
      val url by viewModel.imageUrl
      AsyncImage(
         model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
         placeholder = painterResource(R.drawable.ic_launcher_background),
         contentDescription = stringResource(R.string.image),
         contentScale = ContentScale.FillWidth,
         modifier = Modifier
            .clip(RoundedCornerShape(12.dp)),
      )
   }
}