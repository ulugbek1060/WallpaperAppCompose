package uz.mobidev.wallpaperapp.data.database.entity.image


data class ImageUrlsEntity(
   val full: String,
   val raw: String,
   val regular: String,
   val small: String,
   val small_s3: String,
   val thumb: String
)
