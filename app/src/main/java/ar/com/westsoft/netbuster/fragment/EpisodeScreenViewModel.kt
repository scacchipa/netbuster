package ar.com.westsoft.netbuster.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.data.type.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeScreenViewModel @Inject constructor() : ViewModel() {

    private val _episodeSF = MutableStateFlow(Episode("", -1, -1, "", "", "", ""))
    val episodeSF: StateFlow<Episode> = _episodeSF

    fun setEpisode(episode: Episode) {
        viewModelScope.launch { _episodeSF.emit(episode) }
    }
}