package ar.com.westsoft.netbuster.core

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class TvAPIClient(val context: Context) {
    companion object {
        lateinit var instance: TvAPIClient
    }
    init {
        instance = this
    }
    val url: String = "https://api.tvmaze.com"
    var queue: RequestQueue = Volley.newRequestQueue(context)
    var imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
        private val mCache = LruCache<String, Bitmap>(2*1024*1024)
        override fun putBitmap(url: String, bitmap: Bitmap) {
            mCache.put(url, bitmap)
        }
        override fun getBitmap(url: String): Bitmap? {
            return mCache[url]
        }
    })

    suspend fun getSyncStringResponse(text: String): String =
        suspendCoroutine { continuation ->
                val stringRequest = StringRequest(
                    text,
                    { response -> continuation.resume(response) },
                    { continuation.resumeWithException(it) })
                queue.add(stringRequest)
        }
    suspend fun getSyncArrayJsonResponse(text: String): JSONArray =
        suspendCoroutine { continuation ->
            val jsonArrayRequest = JsonArrayRequest(
                text,
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(jsonArrayRequest)
        }
    suspend fun getSyncObjectJsonResponse(text: String): JSONObject =
        suspendCoroutine { continuation ->
            val jsonObjRequest = JsonObjectRequest(
                Request.Method.GET,
                text, null,
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(jsonObjRequest)
        }

    suspend fun getSyncSerieStringResponse(text: String): String =
        getSyncStringResponse("$url/search/shows?q=$text")

    suspend fun getSyncSerieArrayJsonResponse(text: String): JSONArray =
        getSyncArrayJsonResponse("$url/search/shows?q=$text")

    suspend fun getSyncEpisodeArrayJsonResponse(serie: Int): JSONArray =
        getSyncArrayJsonResponse("$url/shows/$serie/episodes")

    suspend fun getSyncEpisodeInfoJsonResponse(episodeId: Int): JSONObject =
        getSyncObjectJsonResponse("$url/episodes/$episodeId")
}


