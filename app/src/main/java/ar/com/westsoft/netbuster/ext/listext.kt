package ar.com.westsoft.netbuster.ext

import ar.com.westsoft.netbuster.data.type.Series
import org.json.JSONArray

@JvmName("seriesListToJSONArray")
fun List<Series>.toJSONArray(): JSONArray {
    return JSONArray().apply {
        this@toJSONArray.forEach { series ->
            put(series.toJSONObject())
        }
    }
}

@JvmName("stringListToJSONArray")
fun List<String>.toJSONArray(): JSONArray {
    return JSONArray().apply {
        this@toJSONArray.forEach { string ->
            put(string)
        }
    }
}