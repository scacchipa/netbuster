package ar.com.westsoft.netbuster

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
//    }
}

class SerieData(val name: String, val poster: AppCompatImageView)
class SerieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.cardview_image)
    val text: TextView = view.findViewById(R.id.cardview_text)
}

class SerieAdapter(val context: Context, val flowerList: List<SerieData>)
    : Adapter<SerieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
