package ar.com.westsoft.netbuster.ui.screen.poster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.type.Poster
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.usecase.GetImageLoaderUseCase
import ar.com.westsoft.netbuster.usecase.UpdateSeasonTreeUseCase
import com.android.volley.toolbox.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosterViewModel @Inject constructor(
    private val updateSeasonTreeUseCase: UpdateSeasonTreeUseCase,
    private val getImageLoaderUseCase: GetImageLoaderUseCase,
) : ViewModel() {
    private val _posterSF = MutableStateFlow(Poster())
    val posterSF: StateFlow<Poster> = _posterSF

    fun updateSeasonTree(series: Series) {
        viewModelScope.launch() {
            _posterSF.emit(updateSeasonTreeUseCase(series))
        }
    }

    fun setSeries(series: Series) {
        viewModelScope.launch {
            _posterSF.emit(Poster(series = series))
            updateSeasonTree(series)
        }
    }

    fun getImageLoader(): ImageLoader = getImageLoaderUseCase()
}