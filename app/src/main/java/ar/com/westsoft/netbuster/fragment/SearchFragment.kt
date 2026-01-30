package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.ui.screen.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment(private val callback: MainActivity) : Fragment() {

    @Inject lateinit var tvAPIClient: TvAPIClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                var searchQuery by remember { mutableStateOf("") }
                val searchVM by viewModels<SearchViewModel>()
                val state by searchVM.searchSF.collectAsState()
                SearchScreen(
                    searchQuery = searchQuery,
                    onQueryChanged = { str -> searchQuery = str },
                    onClearTapped = { searchQuery = "" },
                    onSearchTapped = { searchVM.searchSeries(searchQuery) },
                    onFavoriteTapped = { searchVM.toggleFavorite(it) },
                    onSeriesTapped = { callback.showSeriesPoster(it.toSeries()) },
                    seriesList = state,
                    imageLoader = tvAPIClient.imageLoader
                )
            }
        }
    }
}