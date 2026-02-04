package ar.com.westsoft.netbuster.data.source

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.di.IoDispatcher
import ar.com.westsoft.netbuster.ext.map
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TvAPIClient @Inject constructor(
    @param:ApplicationContext val context: Context,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    val url: String = "https://api.tvmaze.com"
    var queue: RequestQueue = Volley.newRequestQueue(context)
    var imageLoader = ImageLoader(queue, object : ImageLoader.ImageCache {
        private val mCache = LruCache<String, Bitmap>(2 * 1024 * 1024)
        override fun putBitmap(url: String, bitmap: Bitmap) {
            mCache.put(url, bitmap)
        }

        override fun getBitmap(url: String): Bitmap? {
            return mCache[url]
        }
    })

    suspend fun getSyncStringResponse(text: String): String = withContext(ioDispatcher) {
        suspendCoroutine { continuation ->
            val stringRequest = StringRequest(
                text,
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(stringRequest)
        }
    }
    suspend fun getSyncArrayJsonResponse(text: String): JSONArray = withContext(ioDispatcher) {
        suspendCoroutine { continuation ->
            val jsonArrayRequest = JsonArrayRequest(
                text,
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(jsonArrayRequest)
        }
    }
    suspend fun getSyncObjectJsonResponse(text: String): JSONObject = withContext(ioDispatcher) {
        suspendCoroutine { continuation ->
            val jsonObjRequest = JsonObjectRequest(
                Request.Method.GET,
                text, null,
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(jsonObjRequest)
        }
    }

    suspend fun getSyncSeriesStringResponse(text: String): String =
        getSyncStringResponse("$url/search/shows?q=$text")

    suspend fun getSyncSeriesArrayJsonResponse(text: String): JSONArray =
        getSyncArrayJsonResponse("$url/search/shows?q=$text")

    suspend fun getSyncSeriesListResponse(text: String): List<Series> =
        getSyncArrayJsonResponse("$url/search/shows?q=$text").map { Series.fromJson(it) }

    suspend fun getSyncEpisodeArrayJsonResponse(series: Int): JSONArray =
        getSyncArrayJsonResponse("$url/shows/$series/episodes")
}