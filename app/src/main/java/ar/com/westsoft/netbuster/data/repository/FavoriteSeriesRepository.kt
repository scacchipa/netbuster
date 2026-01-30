package ar.com.westsoft.netbuster.data.repository

import ar.com.westsoft.netbuster.data.source.LocalStore
import ar.com.westsoft.netbuster.data.type.Series
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FavoriteSeriesRepository @Inject constructor(
    private val localStore: LocalStore
) {

    fun getFavoriteSeriesStateFlow(): StateFlow<List<Series>> = localStore.favoriteSeriesSF

    fun getFavoriteSeries(): List<Series> {
        return localStore.retrieveFavoriteSeries()
    }

    suspend fun saveFavoriteSeries(seriesList: List<Series>) {
        localStore.storeFavoriteSeriesList(seriesList)
    }

    suspend fun toggleFavorite(series: Series) {
        localStore.toggleFavorite(series)
    }
}