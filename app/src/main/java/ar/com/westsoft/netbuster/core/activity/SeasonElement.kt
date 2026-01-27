package ar.com.westsoft.netbuster.core.activity

class SeasonElement(
    val seriesTitle: String,
    val seasonId: Int,
    val episodesMap: Map<Int, EpisodeElement> = emptyMap()
) {
    fun addEpisode(episodeElement: EpisodeElement): SeasonElement {
        return SeasonElement(
            seriesTitle = seriesTitle,
            seasonId = seasonId,
            episodesMap = episodesMap + (episodeElement.episodeId to episodeElement)
        )
    }

    override fun toString(): String = "Season = $seasonId, episodesList = $episodesMap"
}