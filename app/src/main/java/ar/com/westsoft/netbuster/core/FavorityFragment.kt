package ar.com.westsoft.netbuster.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R

class FavorityFragment(val callback: MainActivity) : Fragment() {

    var serieAdapter: SerieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.favority_layout, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.favority_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        serieAdapter = SerieAdapter(callback, callback.favoritySerieArray)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()

        return rootView
    }
}

