package ar.com.westsoft.netbuster.ui.screen.poster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.Poster
import ar.com.westsoft.netbuster.data.type.SeasonTree
import ar.com.westsoft.netbuster.data.type.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosterViewModel @Inject constructor(
    private val tvAPIClient: TvAPIClient,
) : ViewModel() {
    private val _posterSF = MutableStateFlow(Poster())
    val posterSF: StateFlow<Poster> = _posterSF

    fun updateSeasonTree(series: Series) {
        viewModelScope.launch() {
            _posterSF.emit(
                Poster(
                    series = series,
                    seasonTree = SeasonTree.createFromJsonArray(
                        seriesTitle = series.title,
                        episodesJSONArray = tvAPIClient.getSyncEpisodeArrayJsonResponse(series.id)
                    ),
                )
            )
        }
    }

    fun setSeries(series: Series) {
        viewModelScope.launch {
            _posterSF.emit(Poster(series = series))
            updateSeasonTree(series)
        }
    }

    fun getTvAPIClient() = tvAPIClient
}