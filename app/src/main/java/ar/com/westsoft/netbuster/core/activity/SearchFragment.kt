package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ar.com.westsoft.netbuster.core.ext.map
import kotlinx.coroutines.launch

class SearchFragment(private val callback: MainActivity) : Fragment() {

    companion object {
        const val tag = "Poster Fragment"
    }

    var seriesList by mutableStateOf(emptyList<Series>())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {

            setContent {
                var searchQuery by remember { mutableStateOf("") }

                SearchScreen(
                    searchQuery = searchQuery,
                    onQueryChanged = { str -> searchQuery = str },
                    onClearTapped = { searchQuery = "" },
                    onSearchTapped = {
                        lifecycleScope.launch {
                            seriesList = callback.tvAPIClient
                                ?.getSyncSerieArrayJsonResponse(searchQuery)
                                ?.map { Series.fromJson(it, callback.favoriteSeriesList) }
                                ?: emptyList()
                        }
                    },
                    onFavoriteTapped = { favoriteSeries ->
                        lifecycleScope.launch {
                            val favoriteList = callback.toggleFavorite(favoriteSeries)

                            seriesList = seriesList.map { series ->
                                if (favoriteList.any { favorite -> favorite.id == series.id }) {
                                    series.copy(isFavorite = true)
                                } else {
                                    series.copy(isFavorite = false)
                                }
                            }
                        }
                    },
                    onSeriesTapped = { callback.showSeriesPoster(it)},
                    seriesList = seriesList
                )
            }
        }
    }
}



