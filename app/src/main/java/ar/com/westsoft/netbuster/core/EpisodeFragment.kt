package ar.com.westsoft.netbuster.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.R
import com.android.volley.toolbox.NetworkImageView
import org.json.JSONException
import org.json.JSONObject


class EpisodeFragment(val callback: MainActivity)
    : Fragment() {
    var jsonObjEpisode: JSONObject? = null
    var serieTitle: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.episode_info, container, false)
        refreshView(rootView)
        return rootView
    }

    fun setInfo(jsonObjEpisode: JSONObject, serieTitle: String) {
        this.jsonObjEpisode = jsonObjEpisode
        this.serieTitle = serieTitle
    }
    fun refreshView(rootView: View) {
        if (jsonObjEpisode != null) {
            val serieTitleTV = rootView.findViewById<TextView>(R.id.serie_title)
            val episodeTitleTV = rootView.findViewById<TextView>(R.id.episode_title)
            val seasonNumberTV = rootView.findViewById<TextView>(R.id.season_number)
            val episodeNumberTV = rootView.findViewById<TextView>(R.id.episode_number)
            val episodeIV = rootView.findViewById<NetworkImageView>(R.id.episode_image)
            val summaryWV = rootView.findViewById<WebView>(R.id.summary_view)
            val backIB = rootView.findViewById<ImageButton>(R.id.back_button)
            val goToPageB = rootView.findViewById<Button>(R.id.go_to_page)

            serieTitleTV?.text = serieTitle
            episodeTitleTV?.text = jsonObjEpisode?.getString("name")
            seasonNumberTV?.text = "Season: " + jsonObjEpisode?.getInt("season")
            try {
                episodeNumberTV?.text = "Episode: " + jsonObjEpisode?.getInt("number")
                episodeIV?.setImageUrl(
                    jsonObjEpisode?.getJSONObject("image")?.getString("medium"),
                    TvAPIClient.instance.imageLoader)
            } catch (e : JSONException) { e.printStackTrace() }
            summaryWV?.loadData(jsonObjEpisode?.getString("summary"), "text/html", "UTF-8")
            backIB?.setOnClickListener { callback.showSeriePoster() }
            goToPageB?.setOnClickListener {
                println(jsonObjEpisode?.getString("url"))
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(jsonObjEpisode?.getString("url")))
                startActivity(browserIntent)
            }
        }
    }
}