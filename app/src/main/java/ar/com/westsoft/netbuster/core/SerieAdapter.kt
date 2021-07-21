package ar.com.westsoft.netbuster.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import org.json.JSONArray
import org.json.JSONException

class SerieAdapter(val callback: MainActivity, val serieArray: JSONArray, val favorityArray: JSONArray)
    : RecyclerView.Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val cardView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SerieViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
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
            val favorityIdPos: Int? = favoriteArrayContainsId(id)
            if (favorityIdPos == null) {
                callback.appendToFavoryArray(serieArray.getJSONObject(position))
                callback.favoritySerieAdapter?.notifyItemInserted(position)
            }
            else {
                callback.removeFromFavorityArray(favorityIdPos)
                callback.favoritySerieAdapter?.notifyItemRemoved(favorityIdPos)
            }
            updateStarIV(holder, id)

            with(PreferenceManager.getDefaultSharedPreferences(callback.baseContext).edit()) {
                this?.putString("favoriteSeries", callback.favoritySerieArray.toString())
                this?.apply()
            }
        }
    }

    override fun getItemCount(): Int {
        return serieArray.length()
    }
    private fun favoriteArrayContainsId(favoriteId: Int) : Int? {
        for (idx in 0 until favorityArray.length())
            if (favorityArray.getJSONObject(idx).getJSONObject("show").getInt("id") == favoriteId)
                return idx
        return null
    }
    private fun updateStarIV(holder: SerieViewHolder, id: Int) {
        if (favoriteArrayContainsId(id) != null)
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_on)
        else
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_off)
    }
}