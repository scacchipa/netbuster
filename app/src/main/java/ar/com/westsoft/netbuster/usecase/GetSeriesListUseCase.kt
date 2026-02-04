package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.SeriesListRepository
import ar.com.westsoft.netbuster.data.type.Series
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSeriesListUseCase @Inject constructor(
    private val seriesListRepository: SeriesListRepository,
) {
    operator fun invoke(): StateFlow<List<Series>> =
        seriesListRepository.getSeriesListStateFlow()
}