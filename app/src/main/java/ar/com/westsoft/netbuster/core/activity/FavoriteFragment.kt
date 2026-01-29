package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class FavoriteFragment(private val callback: MainActivity) : Fragment() {

    var favoriteSeriesListState by mutableStateOf(callback.favoriteSeriesList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                FavoriteScreen(
                    seriesList = favoriteSeriesListState,
                    onFavoriteTapped = { favoriteSeries ->
                        lifecycleScope.launch {
                            favoriteSeriesListState = callback.toggleFavorite(favoriteSeries)
                        }
                    },
                    onSeriesTapped = { callback.showSeriesPoster(it) },
                )
            }
        }
    }

    fun updateFavoriteSeriesList() {
        favoriteSeriesListState = callback.favoriteSeriesList
    }
}

