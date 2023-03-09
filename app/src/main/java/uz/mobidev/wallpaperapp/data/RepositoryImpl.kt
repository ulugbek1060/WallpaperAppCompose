package uz.mobidev.wallpaperapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.mobidev.wallpaperapp.domain.Repository
import uz.mobidev.wallpaperapp.domain.models.WallpaperEntity
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
   private val api: Api
) : Repository {

   override fun getWallpapers() = flow {
      val result = api.newPhotosList(page = 1, perPage = 20).map {
         WallpaperEntity.fromResponse(it)
      }
      emit(result)
   }
      .asResponseState()
      .flowOn(Dispatchers.IO)

}

fun <T> Flow<T>.asResponseState(): Flow<ResponseState<T>> =
   map<T, ResponseState<T>> { ResponseState.Success(it) }
      .onStart { emit(ResponseState.Loading) }
      // With this method can catch any errors from network or internal.
      .catch { e -> emit(ResponseState.Error(e.message ?: "Unknown error")) }

sealed class ResponseState<out T> {
   data class Success<out T>(val data: T) : ResponseState<T>()
   data class Error(val message: String) : ResponseState<Nothing>()
   object Loading : ResponseState<Nothing>()

   fun getValueOrNull(): T? {
      if (this is Success<T>) return this.data
      return null
   }

   fun isFinished() = this is Success<T> || this is Error
}
