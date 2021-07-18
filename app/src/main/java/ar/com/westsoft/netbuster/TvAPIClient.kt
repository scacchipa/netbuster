package ar.com.westsoft.netbuster

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TvAPIClient(context: Context) {
    val url: String = "https://api.tvmaze.com/"
    var queue: RequestQueue = Volley.newRequestQueue(context)

    fun getRequest() {

        val stringRequest = StringRequest(
            "$url/search/shows?q=:query",
            { response ->
                Log.i("Response", "Response is: ${response.substring(1..100)}")
            },
            { error ->
                error.printStackTrace()
            })
        queue.add(stringRequest)
    }

    suspend fun getSyncResponse(): String =
        suspendCoroutine { continuation ->
                val stringRequest = StringRequest(
                    "$url/search/shows?q=:query",
                    { response -> continuation.resume(response) },
                    { continuation.resumeWithException(it) })
                queue.add(stringRequest)
        }
    suspend fun getSyncArrayJsonResponse(): JSONArray =
        suspendCoroutine { continuation ->
            val stringRequest = JsonArrayRequest(
                "$url/search/shows?q=:query",
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(stringRequest)
        }

}