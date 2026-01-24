package ar.com.westsoft.netbuster.core.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.ui.ExpandableEpisodesList
import ar.com.westsoft.netbuster.core.ExpandableSeasonList
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import com.android.volley.toolbox.NetworkImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class PosterFragment(val callback: MainActivity) : Fragment() {
    var serieJsonObj: JSONObject? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_poster, container, false)
        refreshView(rootView)
        return rootView
    }
    private fun refreshView(view: View) {
        if (serieJsonObj != null) {
            val posterImage: NetworkImageView = view.findViewById(R.id.poster_image)
            val name: TextView = view.findViewById(R.id.name)
            val schedule: TextView = view.findViewById(R.id.schedule)
            val gender: TextView = view.findViewById(R.id.gender)
            val episodesView: ExpandableSeasonList = view.findViewById(R.id.episodeDisplay)
            val summary: WebView = view.findViewById(R.id.summary)

            posterImage.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
            try {
                posterImage.setImageUrl(
                    serieJsonObj!!.getJSONObject("image").getString("original"),
                    TvAPIClient.instance.imageLoader
                )
            } catch (e: JSONException) { e.printStackTrace() }
            name.text = serieJsonObj!!.getString("name")

            val scheduleObj = serieJsonObj!!.getJSONObject("schedule")
            var scheduleText = scheduleObj.getString("time")
            val daysObjs = scheduleObj.getJSONArray("days")
            if (daysObjs.length() > 0) {
                scheduleText += " ("
                for (idx in 0 until daysObjs.length()) {
                    scheduleText += daysObjs.getString(idx)
                    if (idx != daysObjs.length() - 1) scheduleText += " - "
                }
                scheduleText += ")"
            }
            schedule.text = scheduleText

            val genresArray = serieJsonObj!!.getJSONArray("genres")
            var genresText = ""
            for (idx in 0 until genresArray.length()) genresText += " " + genresArray.getString(idx)
            gender.text = genresText

            try {
                summary.loadData(serieJsonObj!!.getString("summary"), "text/html", "UTF_8")
            } catch (e: JSONException) { e.printStackTrace() }

            setEpisodes(serieJsonObj!!.getInt("id"), serieJsonObj!!.getString("name"), episodesView)
        }
    }

    private fun setEpisodes(id: Int, serieTitle: String, episodesView: ExpandableSeasonList) {
        GlobalScope.launch {
            val episodesJSONArray = callback.tvAPIClient!!.getSyncEpisodeArrayJsonResponse(id)

            val epiTree = SeasonTree()

            for (idx in 0 until episodesJSONArray.length()) {
                val jsonEpisode = episodesJSONArray[idx] as JSONObject
                epiTree.appendEpisode(
                    callback,
                    serieTitle,
                    jsonEpisode.getInt("season"),
                    jsonEpisode.getInt("number"),
                    jsonEpisode)
            }
            epiTree.sort()
            activity?.runOnUiThread {
                episodesView.setTitle("${epiTree.count()} Seasons")
                epiTree.createTreeView(episodesView.findViewById(R.id.season_list))
            }
        }
    }
}

class SeasonTree {
    private val seasonList: MutableList<SeasonElement> = emptyList<SeasonElement>().toMutableList()

    fun appendEpisode(callback: MainActivity, serieTitle: String, season: Int, number: Int, jsonObject: JSONObject) {
        var seasonElement = seasonList.find { it.season == season }

        if (seasonElement == null) {
            seasonElement = SeasonElement(season)
            seasonList.add(seasonElement)
        }
        seasonElement.appendEpisode(callback, serieTitle, number, jsonObject)
    }
    fun sort() {
        seasonList.sortBy { it.season }
        seasonList.forEach { it.sort() }
    }
    fun count() = seasonList.size
    fun createTreeView(sessionListView: LinearLayout)  {
        seasonList.forEach { seasonElement ->
            val seasonView = ExpandableEpisodesList(sessionListView.context)
            seasonView.setTitle("Season ${seasonElement.season}")
            sessionListView.addView(seasonView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
            seasonElement.appendEpisodeListView(seasonView.findViewById(R.id.episodeListView))
        }
    }
}
class SeasonElement(val season: Int) {
    private val episodesList: MutableList<EpisodeElement> = emptyList<EpisodeElement>().toMutableList()
    fun appendEpisode(callback: MainActivity, serieTitle: String, number: Int, jsonObject: JSONObject) {
        var episodeElement = episodesList.find { it.number == number }

        if (episodeElement == null) {
            episodeElement = EpisodeElement(callback, serieTitle , number, jsonObject)
            episodesList.add(episodeElement)
        }
    }
    fun sort() {
        episodesList.sortBy { it.number }
    }
    fun appendEpisodeListView(episodesListView: LinearLayout){
        episodesList.forEach { episodeElement ->
            val seasonView = episodeElement.createView(episodesListView.context)
            episodesListView.addView(seasonView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }
}
class EpisodeElement(private val callback: MainActivity, val serieTitle: String,
                     val number: Int, val jsonObject: JSONObject) {
    fun createView(ctx: Context): View {
        val textView = TextView(ctx)
        textView.text = "$number - ${jsonObject.getString("name")}"
        textView.textSize = 16f
        textView.setOnClickListener { callback.showEpisodeInfo(jsonObject, serieTitle) }
        return textView
    }
}