package uz.mobidev.wallpaperapp.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mobidev.wallpaperapp.domain.ImageModel
import uz.mobidev.wallpaperapp.domain.Repository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
   private val repository: Repository
) : ViewModel() {

   private val _query = mutableStateOf("")
   val searchQuery = _query

   private val _searchImages = MutableStateFlow<PagingData<ImageModel>>(
      PagingData.empty(
         sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true)
         )
      )
   )
   val searchImages = _searchImages.asStateFlow()

   fun updateSearchQuery(query: String) {
      _query.value = query
   }

   fun searchImages(query: String) = viewModelScope.launch(Dispatchers.IO) {
      if (query == "") return@launch
      repository.getImagesByQuery(query = query)
         .cachedIn(viewModelScope)
         .collectLatest {
            _searchImages.value = it
         }
   }
}