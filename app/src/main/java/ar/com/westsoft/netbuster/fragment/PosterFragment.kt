package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.SeasonTree
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ext.findOrNull
import ar.com.westsoft.netbuster.ui.screen.PosterScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PosterFragment(val callback: MainActivity) : Fragment() {

    var series: Series? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = ComposeView(requireContext()).apply {
            setContent {
                val schedule = series?.schedule

                val seasonTree by produceState(initialValue = null as SeasonTree?) {
                    launch(Dispatchers.IO) {
                        val serialId = series?.id ?: -1

                        value = SeasonTree.Companion.createFromJsonArray(
                            seriesTitle = series?.title ?: "",
                            episodesJSONArray = callback.tvAPIClient!!.getSyncEpisodeArrayJsonResponse(
                                serialId
                            )
                        )
                        println("******* value: $value")
                    }
                }

                val scheduleText = schedule?.time +
                        if  (schedule?.days?.isNotEmpty() ?: false)
                            schedule.days.joinToString(" - ", " (", ")")
                        else ""

                PosterScreen(
                    imageUrl = series?.imageUrl ?: "",
                    imageLoader = TvAPIClient.Companion.instance.imageLoader,
                    nameText = series?.title ?: "",
                    scheduleText = scheduleText,
                    genderText = series?.genres?.joinToString(" "),
                    summaryHtml = series?.summaryHtml ?: "",
                    seasonTree = seasonTree ?: SeasonTree(""),
                    onEpisodeClick = { seasonId, episodeId ->
                        callback.showEpisodeInfo(
                            jsonObject = seasonTree
                                ?.jsonArray?.findOrNull {
                                    it.getInt("season") == seasonId && it.getInt("number") == episodeId
                                }
                                ?: JSONObject(),
                            seriesTitle = series?.title ?: "",
                        )
                    },
                )
            }
        }
        return rootView
    }
}