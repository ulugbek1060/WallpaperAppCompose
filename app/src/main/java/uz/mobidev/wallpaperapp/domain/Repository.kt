package uz.mobidev.wallpaperapp.domain

import kotlinx.coroutines.flow.Flow
import uz.mobidev.wallpaperapp.data.ResponseState
import uz.mobidev.wallpaperapp.domain.models.WallpaperEntity

interface Repository {

   /**
    * Fetch wallpaper
    */
   fun getWallpapers(): Flow<ResponseState<List<WallpaperEntity>>>

}