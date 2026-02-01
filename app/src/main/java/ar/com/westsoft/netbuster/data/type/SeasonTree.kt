package ar.com.westsoft.netbuster.data.type

import org.json.JSONArray
import org.json.JSONObject

class SeasonTree(
    val seriesTitle: String = "",
    val seasonMap: Map<Int, Season> = emptyMap(),
) {
    fun addEpisode(episode: Episode): SeasonTree {

        var season = seasonMap.getOrElse(episode.seasonId) {
            Season(seriesTitle, episode.seasonId)
        }

        season = season.addEpisode(episode)

        return SeasonTree(
            seriesTitle = seriesTitle,
            seasonMap = seasonMap + (episode.seasonId to season),
        )
    }

    companion object {
        fun createFromJsonArray(
            seriesTitle: String,
            episodesJSONArray: JSONArray
        ): SeasonTree {

            var seasonTree = SeasonTree(
                seriesTitle = seriesTitle,
            )

            for (idx in 0 until episodesJSONArray.length()) {
                seasonTree = seasonTree.addEpisode(
                    Episode.fromJson(
                        seriesTitle = seriesTitle,
                        json = episodesJSONArray[idx] as JSONObject)
                )
            }
            return seasonTree
        }
    }

    override fun toString(): String = "seasonList = $seasonMap"
}