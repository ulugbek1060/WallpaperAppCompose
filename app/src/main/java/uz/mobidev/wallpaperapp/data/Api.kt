package uz.mobidev.wallpaperapp.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.mobidev.wallpaperapp.BuildConfig
import uz.mobidev.wallpaperapp.data.models.UnsplashResponse

interface Api {

   companion object {
      const val LATEST = "latest"
      const val OLDEST = "oldest"
      const val POPULAR = "popular"
   }

//   @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
//   @GET("search/photos")
//   suspend fun searchPhotos(
//      @Query("query") query: String,
//      @Query("page") page: Int,
//      @Query("per_page") perPage: Int
//   ): UnsplashResponse

   @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.API_KEY}")
   @GET("/photos")
   suspend fun newPhotosList(
      @Query("page") page: Int,
      @Query("per_page") perPage: Int,
      @Query("order_by") orderBy: String = POPULAR
   ): List<UnsplashResponse>

}