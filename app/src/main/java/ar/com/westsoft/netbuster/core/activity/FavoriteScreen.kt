package ar.com.westsoft.netbuster.core.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.com.westsoft.netbuster.core.client.TvAPIClient

@Composable
fun FavoriteScreen(
    seriesList: List<Series>,
    onSeriesTapped: (Series) -> Unit,
    onFavoriteTapped: (Series) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp), // Reemplaza los márgenes del XML
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Equivale al Adapter del RecyclerView
        items(seriesList) { series ->
            SeriesCard(
                title = series.title,
                imageUrl = series.imageUrl,
                imageLoader = TvAPIClient.instance.imageLoader,
                isFavorite = true,
                onCardClick = { onSeriesTapped(series) },
                onFavoriteClick = { onFavoriteTapped(series) },
            )
        }
    }
}

