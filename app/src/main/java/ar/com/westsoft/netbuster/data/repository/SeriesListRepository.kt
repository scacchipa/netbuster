package ar.com.westsoft.netbuster.data.repository;

import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.source.VolatileSeriesStore
import ar.com.westsoft.netbuster.data.type.Series
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SeriesListRepository @Inject constructor(
    private val tvAPIClient: TvAPIClient,
    private val volatileSeriesStore: VolatileSeriesStore,
) {
    fun getSeriesListStateFlow(): StateFlow<List<Series>> = volatileSeriesStore.seriesList

    suspend fun searchSeries(text: String) {
        volatileSeriesStore.setSeriesList(
            seriesList = tvAPIClient.getSyncSeriesListResponse(text)
        )
    }
}
