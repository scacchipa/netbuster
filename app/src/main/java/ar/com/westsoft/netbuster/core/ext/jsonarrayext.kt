package ar.com.westsoft.netbuster.core.ext

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.joinToString(
    separator: CharSequence = ",",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
): String {
    val builder = StringBuilder()
    if (this.length() > 0) {
        builder.append(prefix)
        for (idx in 0 until this.length()) {
            builder.append(this.getString(idx))
            if (idx != this.length() - 1) builder.append(separator)
        }
        builder.append(postfix)
    }
    return builder.toString()
}

fun JSONArray.findOrNull(predicate: (JSONObject) -> Boolean) : JSONObject? {
    for (idx in 0 until this.length()) {
        val value = this[idx]
        if (value is JSONObject && predicate(value)) return value
    }
    return null
}

fun JSONArray.toList(): List<Any> = buildList { // Use buildList for efficiency
    for (i in 0 until length()) {
        val value = get(i)
        when (value) {
            is JSONArray -> add(value.toList()) // Recursively convert nested arrays
            is JSONObject -> add(value.toMap()) // Convert nested objects to maps
            else -> add(value)
        }
    }
}