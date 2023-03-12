package uz.mobidev.wallpaperapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity

@Dao
interface ImagesDao {

   @Query("SELECT * FROM images_table")
   fun getAllImages(): PagingSource<Int, ImagesEntity>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun saveImages(images: List<ImagesEntity>)

   @Query("DELETE FROM images_table")
   suspend fun deleteImages()

   @Transaction
   suspend fun refresh(images: List<ImagesEntity>) {
      deleteImages()
      saveImages(images = images)
   }
}