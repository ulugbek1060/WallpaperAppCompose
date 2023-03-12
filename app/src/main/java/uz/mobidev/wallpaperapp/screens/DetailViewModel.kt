package uz.mobidev.wallpaperapp.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.mobidev.wallpaperapp.navigation.IMAGE_FULL_URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _imageFullUrl = savedStateHandle.getStateFlow(IMAGE_FULL_URL, null)
   val imageUrl = mutableStateOf<String?>(null)

   init {
      val url = URLDecoder.decode(_imageFullUrl.value, StandardCharsets.UTF_8.toString())
      imageUrl.value = url
   }
}
