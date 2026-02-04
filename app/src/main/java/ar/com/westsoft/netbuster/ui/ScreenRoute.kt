package ar.com.westsoft.netbuster.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ar.com.westsoft.netbuster.enums.NavButton
import ar.com.westsoft.netbuster.enums.Route
import ar.com.westsoft.netbuster.ui.screen.config.ConfigControllerScreen
import ar.com.westsoft.netbuster.ui.screen.episodedetail.EpisodeDetailControllerScreen
import ar.com.westsoft.netbuster.ui.screen.favorite.FavoriteControllerScreen
import ar.com.westsoft.netbuster.ui.screen.poster.PosterControllerScreen
import ar.com.westsoft.netbuster.ui.screen.search.SearchControllerScreen
import ar.com.westsoft.netbuster.ui.widget.BarButton

@Composable
fun MainController() {
    val navController = rememberNavController()
    val items = listOf(NavButton.SEARCH, NavButton.FAVORITE, NavButton.SETTINGS)

    var selectedButton by remember { mutableStateOf(NavButton.SEARCH) }
    var route by remember { mutableStateOf(Route.SEARCH) }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(5f)
            ) {
                items.forEach {
                    BarButton(
                        modifier = Modifier.weight(1f),
                        text = it.label,
                        iconId = it.iconId,
                        isSelected = it == selectedButton,
                        onClick = {
                            route = it.route
                            selectedButton = it
                        })
                }
            }
        }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (route) {
                Route.SEARCH -> SearchControllerScreen(
                    onSeriesTapped = {
                        route = Route.POSTER
                    }
                )
                Route.FAVORITE ->
                    FavoriteControllerScreen(
                        onSeriesTapped = {
                            route = Route.POSTER
                        }
                    )
                Route.SETTINGS -> ConfigControllerScreen(
                    context = navController.context
                )
                Route.POSTER -> PosterControllerScreen(
                    onEpisodeClick = { _, _ -> route = Route.EPISODE }
                )
                Route.EPISODE -> EpisodeDetailControllerScreen(
                    onGoToPoster = { route = Route.POSTER },
                )
            }
        }
    }
}

