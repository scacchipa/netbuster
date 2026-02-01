package ar.com.westsoft.netbuster.fragment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.com.westsoft.netbuster.ui.screen.PosterScreen

@Composable
fun PosterControllerScreen(
    posterVM: PosterViewModel = hiltViewModel(),
    episodeVM: EpisodeScreenViewModel = hiltViewModel(),
    onEpisodeClick: (seasonId: Int, episodeId: Int) -> Unit,
) {
    val state by posterVM.posterSF.collectAsState()

    PosterScreen(
        poster = state,
        imageLoader = posterVM.getTvAPIClient().imageLoader,
        onEpisodeClick = { seasonId, episodeId ->
            episodeVM.setEpisode(
                state.seasonTree?.seasonMap[seasonId]?.episodesMap[episodeId]
                    ?: return@PosterScreen
            )
            onEpisodeClick(seasonId, episodeId)
        },
    )
}