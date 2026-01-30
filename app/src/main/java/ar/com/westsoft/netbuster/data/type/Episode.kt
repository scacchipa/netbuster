package ar.com.westsoft.netbuster.data.type

import org.json.JSONObject

class Episode(
    val seriesTitle: String,
    val seasonId: Int,
    val episodeId: Int,
    val name: String,
    val url: String,
    val imageUrl: String?,
    val summaryHtml: String,
) {
    companion object {
        fun fromJson(seriesTitle: String, json: JSONObject) = Episode(
            seriesTitle = seriesTitle,
            seasonId = json.optInt("season"),
            episodeId = json.optInt("number"),
            name = json.optString("name"),
            url = json.optString("url"),
            imageUrl = json.getJSONObject("image").optString("medium"),
            summaryHtml = json.optString("summary")
        )
    }

    override fun toString(): String = "$seriesTitle: $episodeId - $name"
}