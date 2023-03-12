package uz.mobidev.wallpaperapp.data.database.entity.remote_key

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.mobidev.wallpaperapp.utils.Constants.IMAGES_REMOTE_KEY_TABLE

@Entity(tableName = IMAGES_REMOTE_KEY_TABLE)
data class ImageRemoteKeys(
   @PrimaryKey(autoGenerate = false) val id: String,
   val prevPage: Int?,
   val nextPage: Int?
)
