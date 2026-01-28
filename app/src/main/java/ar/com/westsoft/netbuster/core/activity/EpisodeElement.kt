package ar.com.westsoft.netbuster.core.activity

class EpisodeElement(
    val seriesTitle: String,
    val seasonId: Int,
    val episodeId: Int,
    val name: String,
) {
    override fun toString(): String = "$seriesTitle: $episodeId - $name"
}