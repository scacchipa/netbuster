package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.FavoriteSeriesRepository
import ar.com.westsoft.netbuster.data.type.FavSeries
import javax.inject.Inject

class ToggleFavoriteCardUseCase @Inject constructor(
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
) {
    suspend operator fun invoke(favSeries: FavSeries) {
        favoriteSeriesRepository.toggleFavorite(favSeries.toSeries())
    }
}