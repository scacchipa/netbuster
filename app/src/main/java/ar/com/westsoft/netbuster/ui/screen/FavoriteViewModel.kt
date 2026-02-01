package ar.com.westsoft.netbuster.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.repository.FavoriteSeriesRepository
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.FavSeries
import com.android.volley.toolbox.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
    private val tvAPIClient: TvAPIClient,
): ViewModel() {

    fun getFavoriteSeries() = favoriteSeriesRepository.getFavoriteSeriesStateFlow()
        .map { seriesList ->
            seriesList.values.map { series ->
                FavSeries(
                    id = series.id,
                    title = series.title,
                    imageUrl = series.imageUrl,
                    schedule = series.schedule,
                    genres = series.genres,
                    summaryHtml = series.summaryHtml,
                    isFavorite = true
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleFavorite(favSeries: FavSeries) {
        viewModelScope.launch() {
            favoriteSeriesRepository.toggleFavorite(favSeries.toSeries()
                .also {
                    println(it)
                })
        }
    }

    fun getImageLoader(): ImageLoader = tvAPIClient.imageLoader
}