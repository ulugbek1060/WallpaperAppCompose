package uz.mobidev.wallpaperapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mobidev.wallpaperapp.data.ResponseState
import uz.mobidev.wallpaperapp.domain.Repository
import uz.mobidev.wallpaperapp.domain.models.WallpaperEntity
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: Repository
) : ViewModel() {

   private val _state = MutableStateFlow(State.emptyState())
   val state: StateFlow<State> = _state

   fun loadItems() = viewModelScope.launch {
      repository.getWallpapers().collectLatest {
         when (it) {
            is ResponseState.Success -> {
               _state.value = _state.value.copy(
                  wallpapers = it.data,
                  isLoading = false,
                  error = null
               )
            }
            is ResponseState.Error -> {
               _state.value = _state.value.copy(
                  isLoading = false,
                  error = it.message
               )
            }
            is ResponseState.Loading -> {
               _state.value = _state.value.copy(
                  isLoading = true,
                  error = null
               )
            }
         }
      }
   }

}

data class State(
   val wallpapers: List<WallpaperEntity>,
   val isLoading: Boolean,
   val error: String?,
) {
   companion object {
      fun emptyState(): State {
         return State(
            wallpapers = emptyList(),
            isLoading = true,
            error = null
         )
      }
   }
}


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