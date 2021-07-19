package ar.com.westsoft.netbuster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.NetworkImageView
import org.json.JSONObject

class PosterFragment(val callback: MainActivity) : Fragment() {
    var jsonObject: JSONObject? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_poster, container, false)
        refreshView(rootView)
        return rootView
    }

    fun setSerie(jsonObject: JSONObject) {
        this.jsonObject = jsonObject
    }
    fun refreshView(view: View) {
        if (jsonObject != null) {
            val posterImage: NetworkImageView = view.findViewById(R.id.poster_image)
            val name: TextView = view.findViewById(R.id.name)
            val schedule: TextView = view.findViewById(R.id.schedule)
            val gender: TextView = view.findViewById(R.id.gender)
            val episodes: TextView = view.findViewById(R.id.episodes)
            val summary: WebView = view.findViewById(R.id.summmary)

            posterImage.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
            posterImage.setImageUrl(
                jsonObject!!.getJSONObject("image").getString("original"),
                TvAPIClient.instance.imageLoader
            )
            name.text = jsonObject!!.getString("name")

            val scheduleObj = jsonObject!!.getJSONObject("schedule")
            val daysObjs = scheduleObj.getJSONArray("days")
            var scheduleText = scheduleObj.getString("time") + " ("
            for (idx in 0 until daysObjs.length()) {
                scheduleText += daysObjs.getString(idx)
                if (idx != daysObjs.length() - 1) scheduleText += " - "
            }
            scheduleText += ")"
            schedule.text = scheduleText

            val genresArray = jsonObject!!.getJSONArray("genres")
            var genresText = ""
            for (idx in 0 until genresArray.length()) genresText += " " + genresArray.getString(idx)
            gender.text = genresText

            episodes.text = "Complete episodies"

            summary.loadData(jsonObject!!.getString("summary"), "text/html", "UTF_8")
        }
    }
}