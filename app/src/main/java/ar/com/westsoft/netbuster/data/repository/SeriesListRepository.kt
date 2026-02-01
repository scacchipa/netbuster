package ar.com.westsoft.netbuster.data.repository;

import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.source.VolatileSeriesStore
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesListRepository @Inject constructor(
    private val tvAPIClient: TvAPIClient,
    private val volatileSeriesStore: VolatileSeriesStore,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getSeriesListStateFlow(): StateFlow<List<Series>> = volatileSeriesStore.seriesList

    suspend fun searchSeries(text: String) {

        volatileSeriesStore.setSeriesList(
            seriesList = withContext(ioDispatcher) {
                tvAPIClient.getSyncSeriesListResponse(text).also {
                    println("List<Series>: $it")
                }
            }
        )
    }
}
