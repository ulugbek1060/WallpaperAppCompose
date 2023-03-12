package uz.mobidev.wallpaperapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.mobidev.wallpaperapp.data.database.dao.ImagesDao
import uz.mobidev.wallpaperapp.data.database.dao.RemoteKeysDao
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity
import uz.mobidev.wallpaperapp.data.paging.ImageRemoteMediator
import uz.mobidev.wallpaperapp.data.remote.ImageApi
import uz.mobidev.wallpaperapp.domain.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
   private val imageApi: ImageApi,
   private val remoteKeysDao: RemoteKeysDao,
   private val imagesDao: ImagesDao,
) : Repository {

   @OptIn(ExperimentalPagingApi::class)
   override fun getImages(): Flow<PagingData<ImagesEntity>> {
      val pagingSourceFactory = { imagesDao.getAllImages() }
      return Pager(
         config = PagingConfig(
            pageSize = 10,
         ),
         remoteMediator = ImageRemoteMediator(
            imageApi = imageApi,
            remoteKeysDao = remoteKeysDao,
            imagesDao = imagesDao
         ),
         pagingSourceFactory = pagingSourceFactory
      )
         .flow
   }

}

//fun <T> Flow<T>.asResponseState(): Flow<ResponseState<T>> =
//   map<T, ResponseState<T>> { ResponseState.Success(it) }
//      .onStart { emit(ResponseState.Loading) }
//      // With this method can catch any errors from network or internal.
//      .catch { e -> emit(ResponseState.Error(e.message ?: "Unknown error")) }
//
//sealed class ResponseState<out T> {
//   data class Success<out T>(val data: T) : ResponseState<T>()
//   data class Error(val message: String) : ResponseState<Nothing>()
//   object Loading : ResponseState<Nothing>()
//
//   fun getValueOrNull(): T? {
//      if (this is Success<T>) return this.data
//      return null
//   }
//
//   fun isFinished() = this is Success<T> || this is Error
//}
