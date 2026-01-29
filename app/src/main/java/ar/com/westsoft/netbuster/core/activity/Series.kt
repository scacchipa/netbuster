package ar.com.westsoft.netbuster.core.activity

import ar.com.westsoft.netbuster.core.ext.toJSONArray
import ar.com.westsoft.netbuster.core.ext.toStringList
import org.json.JSONObject

data class Series(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val schedule: Schedule,
    val genres: List<String>,
    val summaryHtml: String,
    val isFavorite: Boolean,
) {
    companion object {
        fun fromJson(json: JSONObject, favorites: List<Series> = emptyList()): Series? {
            val jsonSeries = json.optJSONObject("show") ?: return null
            val id = jsonSeries.optInt("id")

            return Series(
                id = id,
                title = jsonSeries.optString("name"),
                imageUrl = jsonSeries.optJSONObject("image")?.optString("medium") ?: "",
                schedule = Schedule.fromJson(jsonSeries.optJSONObject("schedule") ?: JSONObject())
                    ?: Schedule(),
                genres = jsonSeries.getJSONArray("genres").toStringList(),
                summaryHtml = jsonSeries.getString("summary") ?: "",
                isFavorite = jsonSeries.optBoolean("isFavorite", false) ||
                        favorites.any { it.id == id },
            )
        }
    }

    fun toJSONObject(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("name", title)
            put("image", JSONObject().apply {
                put("medium", imageUrl)
            })
            put("genres", genres.toJSONArray())
            put("summary", summaryHtml)
            put("isFavorite", isFavorite)
        }
    }
}