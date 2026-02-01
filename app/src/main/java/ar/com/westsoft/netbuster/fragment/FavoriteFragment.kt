package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.ui.screen.FavoriteScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment(private val callback: MainActivity) : Fragment() {

    @Inject lateinit var tvAPIClient: TvAPIClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel by viewModels<FavoriteViewModel>()
                val favoriteSeriesListState = viewModel.getFavoriteSeries().collectAsState()
                FavoriteScreen(
                    seriesList = favoriteSeriesListState.value,
                    onFavoriteTapped = { viewModel.toggleFavorite(it) },
                    onSeriesTapped = { },
                    imageLoader = tvAPIClient.imageLoader
                )
            }
        }
    }
}