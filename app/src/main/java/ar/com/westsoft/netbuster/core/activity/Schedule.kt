package ar.com.westsoft.netbuster.core.activity

import ar.com.westsoft.netbuster.core.ext.toStringList
import org.json.JSONObject

data class Schedule(
    val time: String = "",
    val days: List<String> = listOf(),
) {
    companion object {
        fun fromJson(json: JSONObject): Schedule? {
            return Schedule(
                time = json.optString("time"),
                days = json.optJSONArray("days")?.toStringList() ?: listOf(),
            )
        }
    }
}