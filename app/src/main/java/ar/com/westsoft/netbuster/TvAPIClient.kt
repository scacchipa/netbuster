package ar.com.westsoft.netbuster

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
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
    val url: String = "https://api.tvmaze.com/"
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
                    "$url/search/shows?q=$text",
                    { response -> continuation.resume(response) },
                    { continuation.resumeWithException(it) })
                queue.add(stringRequest)
        }
    suspend fun getSyncArrayJsonResponse(text: String): JSONArray =
        suspendCoroutine { continuation ->
            val stringRequest = JsonArrayRequest(
                "$url/search/shows?q=$text",
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(stringRequest)
        }
}


