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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.favority_layout, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.favority_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        callback.favoritySerieAdapter = SerieAdapter(callback, callback.favoritySerieArray, callback.favoritySerieArray)
        recyclerView.adapter = callback.favoritySerieAdapter
        recyclerView.invalidate()

        return rootView
    }
}

