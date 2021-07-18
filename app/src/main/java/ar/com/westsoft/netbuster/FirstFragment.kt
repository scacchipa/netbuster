package ar.com.westsoft.netbuster

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FirstFragment : Fragment() {

    var serieAdapter: SerieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        val serieList:MutableList<SerieData> =  MutableList(0) { SerieData("", "") }
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager

        serieList.add(SerieData("Stranger thing", null))
        serieList.add(SerieData("KindDoom", null))
        println("serie list size: "+serieList.size)
        serieAdapter = SerieAdapter(requireContext(), serieList)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()
        println("recycle adapter size: "+recyclerView.adapter!!.itemCount)
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
    val imageView: ImageView = view.findViewById(R.id.cardview_image)
    val title: TextView = view.findViewById(R.id.cardview_text)
}

class SerieAdapter(val context: Context, val serieList: List<SerieData>)
    : Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        return SerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        holder.title.text = serieList[position].name
    }

    override fun getItemCount(): Int {
        println("Serie list size" + serieList.size)
        return serieList.size
    }
}
