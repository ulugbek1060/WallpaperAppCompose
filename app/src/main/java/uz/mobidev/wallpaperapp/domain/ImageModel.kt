package uz.mobidev.wallpaperapp.domain

import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity
import uz.mobidev.wallpaperapp.data.remote.models.UnsplashResponse

data class ImageModel(
   val thumb: String,
   val original: String
) {
   companion object {
      fun fromResponse(response: UnsplashResponse): ImageModel {
         return ImageModel(
            thumb = response.urls.thumb,
            original = response.urls.full
         )
      }

      fun fromLocal(data: ImagesEntity): ImageModel {
         return ImageModel(
            thumb = data.imagesUrls.thumb,
            original = data.imagesUrls.full
         )
      }
   }
}