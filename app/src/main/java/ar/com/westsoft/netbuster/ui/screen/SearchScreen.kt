package ar.com.westsoft.netbuster.ui.screen

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.ui.widget.SeriesCard

@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onFavoriteTapped: (Series) -> Unit,
    onSeriesTapped: (Series) -> Unit,
    onClearTapped: () -> Unit,
    onSearchTapped: () -> Unit,
    seriesList: List<Series> // Tu modelo de datos
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onQueryChanged(it) },
                modifier = Modifier
                    .weight(1f) // Toma el espacio restante (Equivale a 0dp)
                    .padding(end = 4.dp),
                placeholder = { Text("Search") },
                singleLine = true,
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onClearTapped() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            )
            IconButton(
                onClick = { onSearchTapped() },
                modifier = Modifier
                    .size(56.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu_search),
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(seriesList.size) {
                val series = seriesList[it]
                SeriesCard(
                    title = series.title,
                    imageUrl = series.imageUrl,
                    imageLoader = TvAPIClient.instance.imageLoader,
                    isFavorite = series.isFavorite,
                    onCardClick = { onSeriesTapped(series) },
                    onFavoriteClick = { onFavoriteTapped(series) },
                )
            }
        }
    }
}