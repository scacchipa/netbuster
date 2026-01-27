package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import ar.com.westsoft.netbuster.core.ext.joinToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PosterFragment(val callback: MainActivity) : Fragment() {

    var seriesJsonObj: JSONObject? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = ComposeView(requireContext()).apply {
            setContent {
                val scheduleObj = seriesJsonObj?.getJSONObject("schedule")

                val seasonTree by produceState(initialValue = null as SeasonTree?) {
                    launch(Dispatchers.IO) {
                        val serialId = seriesJsonObj?.getInt("id") ?: -1

                        value = SeasonTree.createFromJsonArray(
                            seriesTitle = seriesJsonObj?.getString("name") ?: "",
                            episodesJSONArray = callback.tvAPIClient!!.getSyncEpisodeArrayJsonResponse(
                                serialId
                            )
                        )
                        println("******* value: $value")
                    }
                }

                PosterScreen(
                    imageUrl = seriesJsonObj!!.getJSONObject("image").getString("original"),
                    imageLoader = TvAPIClient.instance.imageLoader,
                    nameText = seriesJsonObj?.getString("name") ?: "",
                    scheduleText = scheduleObj?.getString("time") +
                            " " + scheduleObj?.getJSONArray("days")?.joinToString("- ", "(", ")"),
                    genderText = seriesJsonObj?.getJSONArray("genres")?.joinToString(" "),
                    summaryHtml = seriesJsonObj?.getString("summary") ?: "",
                    seasonTree = seasonTree ?: SeasonTree(""),
                )
            }
        }
        return rootView
    }
}