package uz.mobidev.wallpaperapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.mobidev.wallpaperapp.domain.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: Repository
) : ViewModel() {

   val images = repository
      .getImages()
      .cachedIn(viewModelScope)

//   private val _state = MutableStateFlow(State.emptyState())
//   val state: StateFlow<State> = _state
//   fun loadItems() = viewModelScope.launch {
//      repository.getImages()
//   }

}

//data class State(
//   val wallpapers: List<ImageModel>,
//   val isLoading: Boolean,
//   val error: String?,
//) {
//   companion object {
//      fun emptyState(): State {
//         return State(
//            wallpapers = emptyList(),
//            isLoading = true,
//            error = null
//         )
//      }
//   }
//}


//fun CoroutineScope.safeLaunch(
//   onError: (Throwable) -> Unit = {},
//   block: suspend CoroutineScope.() -> Unit
//): Job {
//   return launch {
//      try {
//         block()
//      } catch (e: Exception) {
//         onError(e)
//      }
//   }
//}