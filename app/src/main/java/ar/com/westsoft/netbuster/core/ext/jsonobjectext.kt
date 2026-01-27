package ar.com.westsoft.netbuster.core.ext

import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.toMap(): Map<String, Any> = buildMap {
    val keysItr = keys()
    while (keysItr.hasNext()) {
        val key = keysItr.next()
        val value = get(key)
        when (value) {
            is JSONArray -> put(key, value.toList())
            is JSONObject -> put(key, value.toMap())
            else -> put(key, value)
        }
    }
}