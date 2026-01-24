package ar.com.westsoft.netbuster.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import ar.com.westsoft.netbuster.core.activity.MainActivity
import org.json.JSONArray
import org.json.JSONException

class SeriesAdapter(private val callback: MainActivity, val serieArray: JSONArray,
                    val favoriteArray: JSONArray): RecyclerView.Adapter<SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val cardView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SeriesViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val showJsonObj = serieArray.getJSONObject(position).getJSONObject("show")
        holder.imageV.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
        try {
            holder.imageV.setImageUrl(
                showJsonObj.getJSONObject("image").getString("medium"),
                TvAPIClient.instance.imageLoader
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        holder.imageV.setOnClickListener { callback.showSeriePoster(showJsonObj) }

        holder.titleV.text = showJsonObj.getString("name")

        val id = showJsonObj.getInt("id")

        updateStarIV(holder, id)

        holder.starIV.setOnClickListener {
            val favoriteIdPos: Int? = favoriteArrayContainsId(id)
            if (favoriteIdPos == null) {
                callback.appendToFavoryArray(serieArray.getJSONObject(position))
                callback.favoriteSeriesAdapter?.notifyItemInserted(position)
            }
            else {
                callback.removeFromFavoriteArray(favoriteIdPos)
                callback.favoriteSeriesAdapter?.notifyItemRemoved(favoriteIdPos)
            }
            updateStarIV(holder, id)

            with(PreferenceManager.getDefaultSharedPreferences(callback.baseContext).edit()) {
                this?.putString("favoriteSeries", callback.favoriteSerieArray.toString())
                this?.apply()
            }
        }
    }

    override fun getItemCount(): Int {
        return serieArray.length()
    }
    private fun favoriteArrayContainsId(favoriteId: Int) : Int? {
        for (idx in 0 until favoriteArray.length())
            if (favoriteArray.getJSONObject(idx).getJSONObject("show").getInt("id") == favoriteId)
                return idx
        return null
    }
    private fun updateStarIV(holder: SeriesViewHolder, id: Int) {
        if (favoriteArrayContainsId(id) != null)
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_on)
        else
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_off)
    }
}