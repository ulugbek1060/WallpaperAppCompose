package uz.mobidev.wallpaperapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import uz.mobidev.wallpaperapp.data.database.dao.ImagesDao
import uz.mobidev.wallpaperapp.data.database.dao.RemoteKeysDao
import uz.mobidev.wallpaperapp.data.database.entity.image.ImagesEntity
import uz.mobidev.wallpaperapp.data.database.entity.remote_key.ImageRemoteKeys
import uz.mobidev.wallpaperapp.data.remote.ImageApi


@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
   private val imageApi: ImageApi,
   private val remoteKeysDao: RemoteKeysDao,
   private val imagesDao: ImagesDao,
) : RemoteMediator<Int, ImagesEntity>() {

   override suspend fun load(
      loadType: LoadType,
      state: PagingState<Int, ImagesEntity>
   ): MediatorResult {
      return try {
         val currentPage = when (loadType) {
            LoadType.REFRESH -> {
               val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
               remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
               val remoteKeys = getRemoteKeyForFirstItem(state)
               val prevPage = remoteKeys?.prevPage
                  ?: return MediatorResult.Success(
                     endOfPaginationReached = remoteKeys != null
                  )
               prevPage
            }
            LoadType.APPEND -> {
               val remoteKeys = getRemoteKeyForLastItem(state)
               val nextPage = remoteKeys?.nextPage
                  ?: return MediatorResult.Success(
                     endOfPaginationReached = remoteKeys != null
                  )
               nextPage
            }
         }
         val response = imageApi.newPhotosList(page = currentPage, perPage = 10)
         val endOfPaginationReached = response.isEmpty()

         val prevPage = if (currentPage == 1) null else currentPage - 1
         val nextPage = if (endOfPaginationReached) null else currentPage + 1

         val remoteKeys = response.map {
            ImageRemoteKeys(
               id = it.id,
               prevPage = prevPage,
               nextPage = nextPage
            )
         }
         val images = response.map {
            ImagesEntity.fromRemote(
               response = it
            )
         }

         if (loadType == LoadType.REFRESH) {
            imagesDao.refresh(images = images)
            remoteKeysDao.refresh(remoteKeys = remoteKeys)
         } else if (loadType == LoadType.APPEND) {
            imagesDao.saveImages(images = images)
            remoteKeysDao.saveRemoteKeys(remoteKeys = remoteKeys)
         }

         MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
      } catch (e: Exception) {
         MediatorResult.Error(e)
      }
   }

   private suspend fun getRemoteKeyClosestToCurrentPosition(
      state: PagingState<Int, ImagesEntity>
   ): ImageRemoteKeys? {
      return state.anchorPosition?.let { position ->
         state.closestItemToPosition(position)?.id?.let { id ->
            remoteKeysDao.getRemoteKeys(id = id)
         }
      }
   }

   private suspend fun getRemoteKeyForFirstItem(
      state: PagingState<Int, ImagesEntity>
   ): ImageRemoteKeys? {
      return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
         ?.let { unsplashImage ->
            remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
         }
   }

   private suspend fun getRemoteKeyForLastItem(
      state: PagingState<Int, ImagesEntity>
   ): ImageRemoteKeys? {
      return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
         ?.let { unsplashImage ->
            remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
         }
   }
}