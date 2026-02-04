package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.FavoriteSeriesRepository
import ar.com.westsoft.netbuster.data.type.FavSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteFavSeriesUseCase @Inject constructor(
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
) {
    operator fun invoke(): Flow<List<FavSeries>> =
        favoriteSeriesRepository.getFavoriteSeriesStateFlow()
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
}