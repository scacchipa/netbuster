package ar.com.westsoft.netbuster.ui.screen.poster

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.com.westsoft.netbuster.ui.screen.episodedetail.EpisodeScreenViewModel

@Composable
fun PosterControllerScreen(
    posterVM: PosterViewModel = hiltViewModel(),
    episodeVM: EpisodeScreenViewModel = hiltViewModel(),
    onEpisodeClick: (seasonId: Int, episodeId: Int) -> Unit,
) {
    val state by posterVM.posterSF.collectAsState()

    PosterScreen(
        poster = state,
        imageLoader = posterVM.getImageLoader(),
        onEpisodeClick = { seasonId, episodeId ->
            episodeVM.setEpisode(
                state.seasonTree?.seasonMap[seasonId]?.episodesMap[episodeId]
                    ?: return@PosterScreen
            )
            onEpisodeClick(seasonId, episodeId)
        },
    )
}