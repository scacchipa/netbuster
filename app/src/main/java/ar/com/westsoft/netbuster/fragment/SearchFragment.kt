package ar.com.westsoft.netbuster.fragment

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
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ext.map
import ar.com.westsoft.netbuster.ui.screen.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment(private val callback: MainActivity) : Fragment() {

    companion object {
        const val tag = "Poster Fragment"
    }

    @Inject lateinit var tvAPIClient: TvAPIClient
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
                                ?.map { Series.Companion.fromJson(it, callback.favoriteSeriesList) }
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
                    onSeriesTapped = { callback.showSeriesPoster(it) },
                    seriesList = seriesList,
                    imageLoader = tvAPIClient.imageLoader
                )
            }
        }
    }
}