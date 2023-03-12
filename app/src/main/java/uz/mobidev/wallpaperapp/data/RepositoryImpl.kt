package uz.mobidev.wallpaperapp.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.mobidev.wallpaperapp.data.database.dao.ImagesDao
import uz.mobidev.wallpaperapp.data.database.dao.RemoteKeysDao
import uz.mobidev.wallpaperapp.data.paging.DataLoader
import uz.mobidev.wallpaperapp.data.paging.ImagePageSource
import uz.mobidev.wallpaperapp.data.paging.ImageRemoteMediator
import uz.mobidev.wallpaperapp.data.remote.ImageApi
import uz.mobidev.wallpaperapp.domain.ImageModel
import uz.mobidev.wallpaperapp.domain.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
   private val imageApi: ImageApi,
   private val remoteKeysDao: RemoteKeysDao,
   private val imagesDao: ImagesDao,
) : Repository {

   @OptIn(ExperimentalPagingApi::class)
   override fun getImages(): Flow<PagingData<ImageModel>> {
      val pagingSourceFactory = { imagesDao.getAllImages() }
      return Pager(
         config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
         ),
         remoteMediator = ImageRemoteMediator(
            imageApi = imageApi,
            remoteKeysDao = remoteKeysDao,
            imagesDao = imagesDao
         ), pagingSourceFactory = pagingSourceFactory
      )
         .flow
         .map { pagingData ->
            pagingData.map { launchRoomEntity ->
               ImageModel.fromLocal(launchRoomEntity)
            }
         }
   }

   override fun getImagesByQuery(query: String?): Flow<PagingData<ImageModel>> {

      val loader: DataLoader<ImageModel> = {
         imageApi.searchPhotos(query = query, page = it, DEFAULT_PAGE_SIZE).map { response ->
            ImageModel.fromResponse(response = response)
         }
      }
      return Pager(
         config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false
         ),
         pagingSourceFactory = {
            ImagePageSource(loader, DEFAULT_PAGE_SIZE)
         }
      ).flow

   }

   companion object {
      const val DEFAULT_PAGE_SIZE = 10
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
