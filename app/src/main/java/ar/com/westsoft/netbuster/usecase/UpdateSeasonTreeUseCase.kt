package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.Poster
import ar.com.westsoft.netbuster.data.type.SeasonTree
import ar.com.westsoft.netbuster.data.type.Series
import javax.inject.Inject

class UpdateSeasonTreeUseCase @Inject constructor(
    private val tvAPIClient: TvAPIClient,
) {
    suspend operator fun invoke(series: Series): Poster {
        return Poster(
            series = series,
            seasonTree = SeasonTree.createFromJsonArray(
                seriesTitle = series.title,
                episodesJSONArray = tvAPIClient.getSyncEpisodeArrayJsonResponse(series.id)
            ),
        )
    }
}