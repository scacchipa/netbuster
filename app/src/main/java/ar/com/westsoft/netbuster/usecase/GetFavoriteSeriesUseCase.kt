package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.FavoriteSeriesRepository
import ar.com.westsoft.netbuster.data.type.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
) {
    operator fun invoke(): Flow<Map<Int, Series>> =
        favoriteSeriesRepository.getFavoriteSeriesStateFlow()
}