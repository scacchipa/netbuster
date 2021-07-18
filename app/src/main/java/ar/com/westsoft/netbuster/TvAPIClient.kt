package ar.com.westsoft.netbuster

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
    var imageLoader = ImageLoader(this.queue, LruBitmapCache())


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
                    "$url/search/shows?q=:girl",
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
    suspend fun getByteResponse(): String =
        suspendCoroutine { continuation ->
            val stringRequest = StringRequest(
                "$url/search/shows?q=:girl",
                { response -> continuation.resume(response) },
                { continuation.resumeWithException(it) })
            queue.add(stringRequest)
        }
}

class LruBitmapCache @JvmOverloads constructor(maxSize: Int = defaultLruCacheSize) :
    LruCache<String?, Bitmap?>(maxSize), ImageLoader.ImageCache {
    override fun getBitmap(url: String): Bitmap? {
        return get(url)
    }

    override fun putBitmap(url: String, bitmap: Bitmap) {
        put(url, bitmap)
    }

    companion object {
        val defaultLruCacheSize: Int
            get() {
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
                return maxMemory / 8
            }
    }
}



