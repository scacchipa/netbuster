package ar.com.westsoft.netbuster.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ui.screen.SearchViewModel

@Composable
fun SearchControllerScreen(
    searchVM: SearchViewModel = hiltViewModel(),
    posterVM: PosterViewModel = hiltViewModel(),
    onSeriesTapped: (Series) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val state by searchVM.searchSF.collectAsState()
    SearchScreen(
        searchQuery = searchQuery,
        onQueryChanged = { str -> searchQuery = str },
        onClearTapped = { searchQuery = "" },
        onSearchTapped = { searchVM.searchSeries(searchQuery) },
        onFavoriteTapped = { searchVM.toggleFavorite(it) },
        onSeriesTapped = {
            posterVM.setSeries(it.toSeries())
            onSeriesTapped(it.toSeries())
        },
        seriesList = state,
        imageLoader = searchVM.getImageLoader()
    )
}