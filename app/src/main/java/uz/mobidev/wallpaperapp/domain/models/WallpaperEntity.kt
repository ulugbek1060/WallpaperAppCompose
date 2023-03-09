package uz.mobidev.wallpaperapp.domain.models

import uz.mobidev.wallpaperapp.data.models.UnsplashResponse

data class WallpaperEntity(
   val thumb: String,
   val original: String
) {
   companion object {
      fun fromResponse(response: UnsplashResponse): WallpaperEntity {
         return WallpaperEntity(
            thumb = response.urls.thumb,
            original = response.urls.full
         )
      }
   }
}