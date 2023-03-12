package uz.mobidev.wallpaperapp.domain

import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity
import uz.mobidev.wallpaperapp.data.remote.models.UnsplashResponse

data class ImageModel(
   val regular: String,
   val original: String
) {
   companion object {
      fun fromResponse(response: UnsplashResponse): ImageModel {
         return ImageModel(
            regular = response.urls.regular,
            original = response.urls.full
         )
      }

      fun fromLocal(data: ImagesEntity): ImageModel {
         return ImageModel(
            regular = data.imagesUrls.regular,
            original = data.imagesUrls.full
         )
      }
   }
}