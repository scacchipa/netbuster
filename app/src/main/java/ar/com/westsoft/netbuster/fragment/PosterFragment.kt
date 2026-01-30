package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.SeasonTree
import ar.com.westsoft.netbuster.ext.findOrNull
import ar.com.westsoft.netbuster.ui.screen.PosterScreen
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class PosterFragment(val callback: MainActivity) : Fragment() {

    @Inject lateinit var tvAPIClient: TvAPIClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val posterVM by activityViewModels<PosterViewModel>()
                val state by posterVM.posterSF.collectAsState()

                val schedule = state.series?.schedule
                val scheduleText = (schedule?.time ?: "") +
                        if  (schedule?.days?.isNotEmpty() ?: false)
                            schedule.days.joinToString(" - ", " (", ")")
                        else ""

                PosterScreen(
                    imageUrl = state.series?.imageUrl ?: "",
                    imageLoader = tvAPIClient.imageLoader,
                    nameText = state.series?.title ?: "",
                    scheduleText = scheduleText,
                    genderText = state.series?.genres?.joinToString(" "),
                    summaryHtml = state.series?.summaryHtml ?: "",
                    seasonTree = state.seasonTree ?: SeasonTree(""),
                    onEpisodeClick = { seasonId, episodeId ->
                        callback.showEpisodeInfo(
                            jsonObject = state.seasonTree
                                ?.jsonArray?.findOrNull {
                                    it.getInt("season") == seasonId && it.getInt("number") == episodeId
                                }
                                ?: JSONObject(),
                            seriesTitle = state.series?.title ?: "",
                        )
                    },
                )
            }
        }
    }
}