package ar.com.westsoft.netbuster.core.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import ar.com.westsoft.netbuster.databinding.EpisodeInfoBinding
import org.json.JSONException
import org.json.JSONObject


class EpisodeFragment(private val callback: MainActivity) : Fragment() {
    var jsonObjEpisode: JSONObject? = null
    var seriesTitle: String = ""

    private val binding: EpisodeInfoBinding by lazy {
        EpisodeInfoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        refreshView()
        return binding.root
    }

    fun setInfo(jsonObjEpisode: JSONObject, serieTitle: String) {
        this.jsonObjEpisode = jsonObjEpisode
        this.seriesTitle = serieTitle
    }
    private fun refreshView() {
        if (jsonObjEpisode != null) {
            binding.serieTitle.text = seriesTitle
            binding.episodeTitle.text = jsonObjEpisode?.getString("name")
            binding.seasonNumber.text = "Season: ${jsonObjEpisode?.getInt("season")}"
            try {
                binding.episodeNumber.text = "Episode: ${jsonObjEpisode?.getInt("number")}"
                binding.episodeImage.setImageUrl(
                    jsonObjEpisode?.getJSONObject("image")?.getString("medium"),
                    TvAPIClient.instance.imageLoader)
            } catch (e : JSONException) { e.printStackTrace() }
            binding.summaryView.loadData(jsonObjEpisode?.getString("summary") ?: "", "text/html", "UTF-8")
            binding.backButton.setOnClickListener { callback.showSeriesPoster() }
            binding.goToPage.setOnClickListener {
                println(jsonObjEpisode?.getString("url"))
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    jsonObjEpisode?.getString("url")?.toUri())
                startActivity(browserIntent)
            }
        }
    }
}