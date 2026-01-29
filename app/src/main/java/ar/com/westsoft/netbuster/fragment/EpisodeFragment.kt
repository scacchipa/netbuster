package ar.com.westsoft.netbuster.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.ui.screen.EpisodeDetailScreen
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.component.MainActivity
import org.json.JSONObject

class EpisodeFragment(private val callback: MainActivity) : Fragment() {
    var jsonObjEpisode: JSONObject? = null
    var seriesTitle: String = ""

//    private val binding: EpisodeInfoBinding by lazy {
//        EpisodeInfoBinding.inflate(layoutInflater)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                EpisodeDetailScreen(
                    seriesTitle = seriesTitle,
                    episodeTitle = jsonObjEpisode?.optString("name") ?: "",
                    seasonNumber = "Season: ${jsonObjEpisode?.optInt("season")}",
                    episodeNumber = "Episode: ${jsonObjEpisode?.optInt("number")}",
                    imageUrl = jsonObjEpisode?.getJSONObject("image")?.optString("medium"),
                    imageLoader = TvAPIClient.Companion.instance.imageLoader,
                    summaryHtml = jsonObjEpisode?.optString("summary") ?: "",
                    onBackClick = { callback.showSeriesPoster() },
                    onGoToPageClick = {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            jsonObjEpisode?.optString("url")?.toUri()
                        )
                        startActivity(browserIntent)
                    }
                )
            }
        }
    }

    fun setInfo(jsonObjEpisode: JSONObject, seriesTitle: String) {
        this.jsonObjEpisode = jsonObjEpisode
        this.seriesTitle = seriesTitle
    }
}