package ar.com.westsoft.netbuster

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
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
        assertEquals(tvAPIClient.getSyncArrayJsonResponse().length(), 2)
    }
}