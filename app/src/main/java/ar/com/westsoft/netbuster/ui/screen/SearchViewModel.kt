package ar.com.westsoft.netbuster.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.repository.FavoriteSeriesRepository
import ar.com.westsoft.netbuster.data.repository.SeriesListRepository
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.FavSeries
import ar.com.westsoft.netbuster.di.DefaultDispatcher
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
    private val seriesListRepository: SeriesListRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
    private val tvAPIClient: TvAPIClient,
    @param:DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    val searchSF: StateFlow<List<FavSeries>> = combine(
        seriesListRepository.getSeriesListStateFlow(),
        favoriteSeriesRepository.getFavoriteSeriesStateFlow()
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
            seriesListRepository.searchSeries(searchQuery)
        }
    }

    fun toggleFavorite(favSeries: FavSeries) {
        viewModelScope.launch(defaultDispatcher) {
            favoriteSeriesRepository.toggleFavorite(favSeries.toSeries().also {
                println(it)
            }
            )
        }
    }

    fun getImageLoader(): ImageLoader = tvAPIClient.imageLoader
}