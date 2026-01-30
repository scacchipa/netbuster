package ar.com.westsoft.netbuster.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.ui.screen.EpisodeDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeFragment(private val callback: MainActivity) : Fragment() {

    @Inject lateinit var tvAPIClient: TvAPIClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val episodeVM by activityViewModels<EpisodeScreenViewModel>()
                val state by episodeVM.episodeSF.collectAsState()

                EpisodeDetailScreen(
                    episode = state,
                    imageLoader = tvAPIClient.imageLoader,
                    onBackClick = { callback.showSeriesPoster() },
                    onGoToPageClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, state.url?.toUri())
                        startActivity(browserIntent)
                    }
                )
            }
        }
    }
}