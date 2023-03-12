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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.mobidev.wallpaperapp.R
import uz.mobidev.wallpaperapp.data.database.entity.image.ImageUrlsEntity
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity

@Composable
fun ListContent(
   images: LazyPagingItems<ImagesEntity>,
   onItemClick: (String) -> Unit
) {
   LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
      items(images.itemCount)
      { index ->
         images[index]?.let {
            ItemContent(
               item = it,
               onCLick = onItemClick
            )
         }
      }
   }
}

//@ExperimentalFoundationApi
//fun <T: Any> LazyGridScope.gridItems(
//   lazyPagingItems: LazyPagingItems<T>,
//   itemContent: @Composable LazyItemScope.(value: T?) -> Unit
//) {
//   items(lazyPagingItems.itemCount) { index ->
//      itemContent(lazyPagingItems[index])
//   }
//}
//


@Composable
fun ItemContent(item: ImagesEntity, onCLick: (String) -> Unit) {
   AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
         .data(item.imagesUrls.regular)
         .crossfade(true)
         .build(),
      placeholder = painterResource(R.drawable.ic_launcher_background),
      contentDescription = stringResource(R.string.image),
      contentScale = ContentScale.Crop,
      modifier = Modifier
         .clickable { onCLick(item.imagesUrls.full) }
         .clip(RoundedCornerShape(12.dp)),
   )
}

@Preview
@Composable
fun ItemPreview() {
   ItemContent(
      item = ImagesEntity(
         "",
         ImageUrlsEntity(
            "",
            "",
            "https://i.pinimg.com/originals/99/27/00/992700250f8c10f9564b4d80f8b2f6fd.jpg",
            "",
            "",
            ""
         )
      )
   ) {

   }
}