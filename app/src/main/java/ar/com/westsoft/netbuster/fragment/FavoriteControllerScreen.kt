package ar.com.westsoft.netbuster.fragment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ui.screen.FavoriteScreen

@Composable
fun FavoriteControllerScreen(
    favoriteVM: FavoriteViewModel = hiltViewModel(),
    posterVM: PosterViewModel = hiltViewModel(),
    onSeriesTapped: (Series) -> Unit,
) {
    val favoriteSeriesListState = favoriteVM.getFavoriteSeries().collectAsState()
    FavoriteScreen(
        seriesList = favoriteSeriesListState.value,
        onFavoriteTapped = { favoriteVM.toggleFavorite(it) },
        onSeriesTapped = {
            posterVM.setSeries(it.toSeries())
            onSeriesTapped(it.toSeries())
        },
        imageLoader = favoriteVM.getImageLoader()
    )
}