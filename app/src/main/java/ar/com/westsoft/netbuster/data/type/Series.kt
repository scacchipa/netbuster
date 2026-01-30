package ar.com.westsoft.netbuster.data.type

import ar.com.westsoft.netbuster.ext.toJSONArray
import ar.com.westsoft.netbuster.ext.toStringList
import org.json.JSONObject

data class Series(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val schedule: Schedule,
    val genres: List<String>,
    val summaryHtml: String,
) {
    companion object {
        fun fromJson(json: JSONObject): Series? {
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
            )
        }
    }

    fun toJSONObject(): JSONObject {
        return JSONObject().apply {
            put("show", JSONObject().apply {
                put("id", id)
                put("name", title)
                put("image", JSONObject().apply {
                    put("medium", imageUrl)
                })
                put("genres", genres.toJSONArray())
                put("summary", summaryHtml)
            }
            )
        }
    }
}