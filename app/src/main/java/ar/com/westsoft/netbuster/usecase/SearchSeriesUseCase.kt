package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.SeriesListRepository
import javax.inject.Inject

class SearchSeriesUseCase @Inject constructor(
    private val seriesListRepository: SeriesListRepository,
) {
    suspend operator fun invoke(searchQuery: String) {
        seriesListRepository.searchSeries(searchQuery)
    }
}