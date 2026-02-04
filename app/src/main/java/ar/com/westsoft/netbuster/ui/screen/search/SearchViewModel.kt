package ar.com.westsoft.netbuster.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.type.FavSeries
import ar.com.westsoft.netbuster.di.DefaultDispatcher
import ar.com.westsoft.netbuster.usecase.GetFavoriteSeriesUseCase
import ar.com.westsoft.netbuster.usecase.GetImageLoaderUseCase
import ar.com.westsoft.netbuster.usecase.GetSeriesListUseCase
import ar.com.westsoft.netbuster.usecase.SearchSeriesUseCase
import ar.com.westsoft.netbuster.usecase.ToggleFavoriteCardUseCase
import com.android.volley.toolbox.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSeriesUseCase: SearchSeriesUseCase,
    getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    getSeriesListUseCase: GetSeriesListUseCase,
    private val toggleFavoriteCardUseCase: ToggleFavoriteCardUseCase,
    private val getImageLoaderUseCase: GetImageLoaderUseCase,
    @param:DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    val searchSF: StateFlow<List<FavSeries>> = combine(
        getSeriesListUseCase(),
        getFavoriteSeriesUseCase(),
    ) { seriesList, favoriteList ->
        seriesList.map { series ->
            FavSeries(
                id = series.id,
                title = series.title,
                imageUrl = series.imageUrl,
                schedule = series.schedule,
                genres = series.genres,
                summaryHtml = series.summaryHtml,
                isFavorite = favoriteList[series.id] != null
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun searchSeries(searchQuery: String) {
        viewModelScope.launch {
            searchSeriesUseCase(searchQuery)
        }
    }

    fun toggleFavorite(favSeries: FavSeries) {
        viewModelScope.launch(defaultDispatcher) {
            toggleFavoriteCardUseCase(favSeries)
        }
    }

    fun getImageLoader(): ImageLoader = getImageLoaderUseCase()
}