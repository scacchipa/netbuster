package ar.com.westsoft.netbuster

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.volley.toolbox.NetworkImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

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

        serieAdapter = SerieAdapter(requireContext(), serieArray)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()

        GlobalScope.launch {
            serieArray = callback.tvAPIClient?.getSyncArrayJsonResponse()?:serieArray
            activity?.runOnUiThread {
                recyclerView.adapter = SerieAdapter(requireContext(), serieArray)
                recyclerView.invalidate()
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

class SerieData(val name: String, val poster: String?)
class SerieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: NetworkImageView = view.findViewById(R.id.cardview_image)
    val title: TextView = view.findViewById(R.id.cardview_text)
}

class SerieAdapter(val context: Context, val serieList: JSONArray)
    : Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val show = serieList.getJSONObject(position).getJSONObject("show")

        //holder.imageView.setDefaultImageDrawable(R.drawable.default_image)
        holder.imageView.setImageUrl(
            show.getJSONObject("image").getString("medium"),
            TvAPIClient.instance.imageLoader)
        holder.title.text = show.getString("name")
    }

    override fun getItemCount(): Int {
        return serieList.length()
    }
}
