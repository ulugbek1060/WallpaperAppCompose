package uz.mobidev.wallpaperapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.mobidev.wallpaperapp.R
import uz.mobidev.wallpaperapp.domain.ImageModel

@Composable
fun ListContent(
   images: LazyPagingItems<ImageModel>, onItemClick: (String) -> Unit
) {
   LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
      items(images.itemCount) { index ->
         images[index]?.let {
            ItemContent(
               item = it,
               onCLick = onItemClick
            )
         }
      }
   }
}

@Composable
fun ItemContent(item: ImageModel, onCLick: (String) -> Unit) {
   AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
         .data(item.regular)
         .crossfade(true)
         .build(),
      placeholder = painterResource(R.drawable.ic_launcher_background),
      contentDescription = stringResource(R.string.image),
      contentScale = ContentScale.Crop,
      modifier = Modifier
         .clickable { onCLick(item.original) }
         .clip(RoundedCornerShape(12.dp)),
   )
}