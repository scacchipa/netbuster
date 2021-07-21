package ar.com.westsoft.netbuster.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R

class FavoriteFragment(private val callback: MainActivity) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.favorite_layout, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.favorite_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        callback.favoriteSerieAdapter = SerieAdapter(callback, callback.favoriteSerieArray, callback.favoriteSerieArray)
        recyclerView.adapter = callback.favoriteSerieAdapter
        recyclerView.invalidate()

        return rootView
    }
}

