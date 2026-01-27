package ar.com.westsoft.netbuster.core.activity

import org.json.JSONObject

class EpisodeElement(
    val seriesTitle: String, val seasonId: Int, val episodeId: Int, val name: String,
) {
    constructor(
        seriesTitle: String,
        seasonId: Int,
        episodeId: Int,
        jsonObject: JSONObject,
    ) : this(
            seriesTitle = seriesTitle,
            seasonId = seasonId,
            episodeId = episodeId,
            name = jsonObject.getString("name"),
    )

    override fun toString(): String = "$seriesTitle: $episodeId - $name"
}