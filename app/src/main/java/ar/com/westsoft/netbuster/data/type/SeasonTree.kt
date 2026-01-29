package ar.com.westsoft.netbuster.data.type

import org.json.JSONArray
import org.json.JSONObject

class SeasonTree(
    val seriesTitle: String = "",
    val seasonMap: Map<Int, SeasonElement> = emptyMap(),
    val jsonArray: JSONArray = JSONArray(),
) {
    fun addEpisode(episode: EpisodeElement): SeasonTree {

        var season = seasonMap.getOrElse(episode.seasonId) {
            SeasonElement(seriesTitle, episode.seasonId)
        }

        season = season.addEpisode(episode)

        return SeasonTree(
            seriesTitle = seriesTitle,
            seasonMap = seasonMap + (episode.seasonId to season),
            jsonArray = jsonArray
        )
    }

    companion object {
        fun createFromJsonArray(
            seriesTitle: String,
            episodesJSONArray: JSONArray
        ): SeasonTree {

            var seasonTree = SeasonTree(
                seriesTitle = seriesTitle,
                jsonArray = episodesJSONArray
            )

            for (idx in 0 until episodesJSONArray.length()) {
                val jsonEpisode = episodesJSONArray[idx] as JSONObject

                seasonTree = seasonTree.addEpisode(
                    EpisodeElement(
                        seriesTitle = seriesTitle,
                        seasonId = jsonEpisode.optInt("season"),
                        episodeId = jsonEpisode.optInt("number"),
                        name = jsonEpisode.optString("name"),
                    )
                )
            }
            return seasonTree
        }
    }

    override fun toString(): String = "seasonList = $seasonMap"
}