package ar.com.westsoft.netbuster.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.type.FavSeries
import ar.com.westsoft.netbuster.di.DefaultDispatcher
import ar.com.westsoft.netbuster.usecase.GetFavoriteFavSeriesUseCase
import ar.com.westsoft.netbuster.usecase.GetImageLoaderUseCase
import ar.com.westsoft.netbuster.usecase.ToggleFavoriteCardUseCase
import com.android.volley.toolbox.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val toggleFavoriteCardUseCase: ToggleFavoriteCardUseCase,
    private val getFavoriteFavSeriesUseCase: GetFavoriteFavSeriesUseCase,
    private val getImageLoaderUseCase: GetImageLoaderUseCase,
    @param:DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    fun getFavoriteSeries() = getFavoriteFavSeriesUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleFavorite(favSeries: FavSeries) {
        viewModelScope.launch(defaultDispatcher) {
            toggleFavoriteCardUseCase(favSeries)
        }
    }

    fun getImageLoader(): ImageLoader = getImageLoaderUseCase()
}


