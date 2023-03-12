package uz.mobidev.wallpaperapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

typealias DataLoader<T> = suspend (pageIndex: Int) -> List<T>

class ImagePageSource<T : Any>(
   private val loader: DataLoader<T>,
   private val defaultPageSize: Int
) : PagingSource<Int, T>() {

   override fun getRefreshKey(state: PagingState<Int, T>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
      val pageIndex = params.key ?: STARTING_PAGE_INDEX
      return try {
         val list = loader.invoke(pageIndex)
         LoadResult.Page(
            data = list,
            prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
            nextKey = if (list.isEmpty() || list.size < defaultPageSize) null else pageIndex + 1
         )
      } catch (e: Exception) {
         LoadResult.Error(e)
      }
   }

   private companion object {
      private const val STARTING_PAGE_INDEX = 1
   }
}