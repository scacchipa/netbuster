package ar.com.westsoft.netbuster.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.activity.MainActivity
import ar.com.westsoft.netbuster.core.activity.Series
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import org.json.JSONException

class SeriesAdapter(
    private val callback: MainActivity,
    val seriesList: List<Series>,
    val favoriteArray: List<Series>,
): RecyclerView.Adapter<SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val cardView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SeriesViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val showSeriesObj = seriesList[position]
        holder.imageV.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
        try {
            holder.imageV.setImageUrl(
                showSeriesObj.imageUrl,
                TvAPIClient.instance.imageLoader
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        holder.imageV.setOnClickListener { callback.showSeriesPoster(showSeriesObj) }

        holder.titleV.text = showSeriesObj.title

        val id = showSeriesObj.id

        updateStarIV(holder, id)

        holder.starIV.setOnClickListener {
            val favoriteIdPos: Int? = favoriteArrayContainsId(id)
            if (favoriteIdPos == null) {
                callback.appendToFavoriteArray(seriesList[position])
                callback.favoriteSeriesAdapter?.notifyItemInserted(position)
            }
            else {
                callback.removeFromFavoriteArray(seriesList[position])
                callback.favoriteSeriesAdapter?.notifyItemRemoved(favoriteIdPos)
            }
            updateStarIV(holder, id)

            with(PreferenceManager.getDefaultSharedPreferences(callback.baseContext).edit()) {
                this?.putString("favoriteSeries", callback.favoriteSeriesList.map{it.toJSONObject()}.toString())
                this?.apply()
            }
        }
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }
    private fun favoriteArrayContainsId(favoriteId: Int) : Int? {
        for (idx in 0 until favoriteArray.size)
            if (favoriteArray[idx].id == favoriteId)
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