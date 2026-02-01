package ar.com.westsoft.netbuster.fragment

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.com.westsoft.netbuster.ui.screen.EpisodeDetailScreen

@Composable
fun EpisodeDetailControllerScreen(
    episodeVM: EpisodeScreenViewModel = hiltViewModel(),
    onGoToPoster: () -> Unit,
) {
    val state by episodeVM.episodeSF.collectAsState()
    val context = LocalContext.current

    EpisodeDetailScreen(
        episode = state,
        imageLoader = episodeVM.getTvAPIClient().imageLoader,
        onBackClick = {
            onGoToPoster()
        },
        onGoToPage = {uri ->
                val browserIntent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(browserIntent)
        }
    )
}