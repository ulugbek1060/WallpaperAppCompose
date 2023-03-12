package uz.mobidev.wallpaperapp.data.database.entity.image

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.mobidev.wallpaperapp.data.remote.models.UnsplashResponse
import uz.mobidev.wallpaperapp.utils.Constants.IMAGES_TABLE

@Entity(tableName = IMAGES_TABLE)
data class ImagesEntity(
   @PrimaryKey(autoGenerate = false) val id: String,
   @Embedded val imagesUrls: ImageUrlsEntity
) {
   companion object {
      fun fromRemote(response: UnsplashResponse): ImagesEntity =
         ImagesEntity(
            id = response.id,
            imagesUrls = ImageUrlsEntity(
               full = response.urls.full,
               raw = response.urls.raw,
               regular = response.urls.regular,
               small = response.urls.small,
               small_s3 = response.urls.small_s3,
               thumb = response.urls.thumb
            )
         )
   }
}
