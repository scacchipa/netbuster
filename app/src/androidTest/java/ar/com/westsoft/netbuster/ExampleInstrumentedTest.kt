package ar.com.westsoft.netbuster

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var appContext: Context
    lateinit var tvAPIClient: TvAPIClient
    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        tvAPIClient = TvAPIClient(appContext)
    }
    @Test
    fun useAppContext() {
        assertEquals("ar.com.westsoft.netbuster", appContext.packageName)
    }
    @Test
    fun singleRequest() {
        // Context of the app under test.

        val signal = CountDownLatch(1)
        val url = "https://api.tvmaze.com/"
        var stringResponse = ""
        val stringRequest = StringRequest(
            "$url/search/shows?q=:query",
            { response ->
                stringResponse = response
                signal.countDown()
            },
            { error ->
                error.printStackTrace()
                signal.countDown()
            })

        tvAPIClient.queue.add(stringRequest)
        signal.await()
        assertEquals(stringResponse.length, 3381)
    }
    @Test
    fun asynGetResponse() = runBlocking {
        assertEquals(tvAPIClient.getSyncSerieStringResponse("girl").length, 13536)
        assertEquals(tvAPIClient.getSyncSerieArrayJsonResponse("query").length(), 2)
        assertEquals(tvAPIClient.getSyncEpisodeArrayJsonResponse(1).length(), 39)
    }
}