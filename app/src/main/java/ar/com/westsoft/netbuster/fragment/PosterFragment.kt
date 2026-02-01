package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.ui.screen.PosterScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PosterFragment(val callback: MainActivity) : Fragment() {

    @Inject lateinit var tvAPIClient: TvAPIClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val posterVM by activityViewModels<PosterViewModel>()
                val state by posterVM.posterSF.collectAsState()
                PosterScreen(
                    poster = state,
                    imageLoader = tvAPIClient.imageLoader,
                    onEpisodeClick = { _, _ -> },
                )
            }
        }
    }
}

