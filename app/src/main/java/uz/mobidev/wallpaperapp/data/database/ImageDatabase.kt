package uz.mobidev.wallpaperapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mobidev.wallpaperapp.data.database.dao.ImagesDao
import uz.mobidev.wallpaperapp.data.database.dao.RemoteKeysDao
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity
import uz.mobidev.wallpaperapp.data.database.entity.remote_key.ImageRemoteKeys

@Database(
   entities = [ImagesEntity::class, ImageRemoteKeys::class],
   version = 1
)
abstract class ImageDatabase : RoomDatabase() {
   abstract fun imagesDao(): ImagesDao
   abstract fun remoteKeysDao(): RemoteKeysDao
}