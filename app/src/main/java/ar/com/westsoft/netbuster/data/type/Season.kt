package ar.com.westsoft.netbuster.data.type

class Season(
    val seriesTitle: String,
    val seasonId: Int,
    val episodesMap: Map<Int, Episode> = emptyMap()
) {
    fun addEpisode(episode: Episode): Season {
        return Season(
            seriesTitle = seriesTitle,
            seasonId = seasonId,
            episodesMap = episodesMap + (episode.episodeId to episode)
        )
    }

    override fun toString(): String = "Season = $seasonId, episodesList = $episodesMap"
}