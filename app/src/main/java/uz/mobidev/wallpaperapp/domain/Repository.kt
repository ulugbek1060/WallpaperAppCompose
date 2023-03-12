package uz.mobidev.wallpaperapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity

interface Repository {

   /**
    * Fetch wallpaper
    */
   fun getImages(): Flow<PagingData<ImagesEntity>>

}