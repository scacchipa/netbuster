package ar.com.westsoft.netbuster.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ar.com.westsoft.netbuster.R
import com.android.volley.toolbox.NetworkImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException

class SearchFragment(val callback: MainActivity) : Fragment() {

    var serieAdapter: SerieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        var serieArray =  JSONArray()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        serieAdapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()

        GlobalScope.launch {
            serieArray = callback.tvAPIClient?.getSyncSerieArrayJsonResponse("girl")?:serieArray
            activity?.runOnUiThread {
                recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
                recyclerView.invalidate()
            }
        }

        val searchField:EditText = rootView.findViewById(R.id.search_key)
        val delButton:ImageButton = rootView.findViewById(R.id.delete_text_button)
        val searchButton:ImageButton = rootView.findViewById(R.id.search_button)

        delButton.setOnClickListener { searchField.setText("") }
        searchButton.setOnClickListener {
            GlobalScope.launch {
                serieArray = callback.tvAPIClient?.
                        getSyncSerieArrayJsonResponse(searchField.text.toString())?:serieArray
                activity?.runOnUiThread {
                    recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
                    recyclerView.invalidate()

                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }
        }
        return rootView
    }
}

class SerieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageV: NetworkImageView = view.findViewById(R.id.cardview_image)
    val titleV: TextView = view.findViewById(R.id.cardview_text)
    val starIV: ImageView = view.findViewById(R.id.star_view)
}

class SerieAdapter(val callback: MainActivity, val serieArray: JSONArray, val favorityArray: JSONArray)
    : Adapter<SerieViewHolder>() {

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
            val favorityIdPos: Int? = favoryArrayContainsId(id)
            if (favorityIdPos == null) {
                callback.appendToFavoryArray(serieArray.getJSONObject(position))
                callback.favoritySerieAdapter?.notifyItemInserted(position)
            }
            else {
                callback.removeFromFavorityArray(favorityIdPos)
                callback.favoritySerieAdapter?.notifyItemRemoved(favorityIdPos)
            }
            updateStarIV(holder, id)
        }
    }

    override fun getItemCount(): Int {
        return serieArray.length()
    }
    private fun favoryArrayContainsId(favorityId: Int) : Int? {
        for (idx in 0 until favorityArray.length())
            if (favorityArray.getJSONObject(idx).getJSONObject("show").getInt("id") == favorityId)
                return idx
        return null
    }
    private fun updateStarIV(holder: SerieViewHolder, id: Int) {
        if (favoryArrayContainsId(id) != null)
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_on)
        else
            holder.starIV.setImageResource(android.R.drawable.btn_star_big_off)
    }
}
