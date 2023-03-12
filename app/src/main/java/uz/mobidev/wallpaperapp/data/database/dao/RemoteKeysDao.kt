package uz.mobidev.wallpaperapp.data.database.dao

import androidx.room.*
import uz.mobidev.wallpaperapp.data.database.entity.remote_key.ImageRemoteKeys


@Dao
interface RemoteKeysDao {

   @Query("SELECT * FROM images_remote_key_table WHERE id =:id")
   suspend fun getRemoteKeys(id: String): ImageRemoteKeys

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun saveRemoteKeys(remoteKeys: List<ImageRemoteKeys>)

   @Query("DELETE FROM images_remote_key_table")
   suspend fun deleteRemoteKeys()

   @Transaction
   suspend fun refresh(remoteKeys: List<ImageRemoteKeys>) {
      deleteRemoteKeys()
      saveRemoteKeys(remoteKeys = remoteKeys)
   }
}