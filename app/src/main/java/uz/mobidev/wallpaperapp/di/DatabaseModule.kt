package uz.mobidev.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.mobidev.wallpaperapp.data.database.ImageDatabase
import uz.mobidev.wallpaperapp.data.database.dao.ImagesDao
import uz.mobidev.wallpaperapp.data.database.dao.RemoteKeysDao
import uz.mobidev.wallpaperapp.utils.Constants.IMAGES_DATABASE
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

   @Singleton
   @Provides
   fun getDatabase(@ApplicationContext context: Context): ImageDatabase {
      return Room.databaseBuilder(
         context,
         ImageDatabase::class.java,
         IMAGES_DATABASE
      )
         .build()
   }

   @Singleton
   @Provides
   fun providesImagesDao(database: ImageDatabase): ImagesDao {
      return database.imagesDao()
   }


   @Singleton
   @Provides
   fun providesRemoteKeys(database: ImageDatabase): RemoteKeysDao {
      return database.remoteKeysDao()
   }
}