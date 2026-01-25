package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.adapter.SeriesAdapter
import ar.com.westsoft.netbuster.databinding.FavoriteLayoutBinding

class FavoriteFragment(private val callback: MainActivity) : Fragment() {

    private val binding: FavoriteLayoutBinding by lazy {
        FavoriteLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.favorite_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        callback.favoriteSeriesAdapter = SeriesAdapter(
            callback, callback.favoriteSeriesArray, callback.favoriteSeriesArray
        )
        recyclerView.adapter = callback.favoriteSeriesAdapter
        recyclerView.invalidate()

        return binding.root
    }
}

