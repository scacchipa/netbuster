package ar.com.westsoft.netbuster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.volley.toolbox.NetworkImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SearchFragment(val callback: MainActivity) : Fragment() {

    var serieAdapter: SerieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        var serieArray =  JSONArray()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        serieAdapter = SerieAdapter(callback, serieArray)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()

        GlobalScope.launch {
            serieArray = callback.tvAPIClient?.getSyncArrayJsonResponse("girl")?:serieArray
            activity?.runOnUiThread {
                recyclerView.adapter = SerieAdapter(callback, serieArray)
                recyclerView.invalidate()
            }
        }

        val searchField:EditText = rootView.findViewById(R.id.search_key)
        val delButton:ImageButton = rootView.findViewById(R.id.delete_text_button)
        val searchButton:ImageButton = rootView.findViewById(R.id.search_button)

        delButton.setOnClickListener { searchField.setText("") }
        searchButton.setOnClickListener {
            println(searchField.text.toString())
            GlobalScope.launch {
                serieArray = callback.tvAPIClient?.
                        getSyncArrayJsonResponse(searchField.text.toString())?:serieArray
                activity?.runOnUiThread {
                    recyclerView.adapter = SerieAdapter(callback, serieArray)
                    recyclerView.invalidate()
                }
            }
        }

        return rootView
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
//    }
}

class SerieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: NetworkImageView = view.findViewById(R.id.cardview_image)
    val title: TextView = view.findViewById(R.id.cardview_text)
}

class SerieAdapter(val context: MainActivity, val serieList: JSONArray)
    : Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val cardView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SerieViewHolder(cardView)
    }
    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val jsonObject = serieList.getJSONObject(position).getJSONObject("show")

        holder.imageView.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
        holder.imageView.setImageUrl(
            jsonObject.getJSONObject("image").getString("medium"),
            TvAPIClient.instance.imageLoader)
        holder.imageView.setOnClickListener { context.showPoster(jsonObject) }

        holder.title.text = jsonObject.getString("name")

    }
    override fun getItemCount(): Int {
        return serieList.length()
    }
}
