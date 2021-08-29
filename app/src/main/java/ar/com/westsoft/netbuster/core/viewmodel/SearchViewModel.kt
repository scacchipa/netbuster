package ar.com.westsoft.netbuster.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray

class SearchViewModel: ViewModel() {
    val searchSerieArray = MutableLiveData<JSONArray>()
    fun onSearchSerie(txt: String) {

    }
    fun onSearchArrayWasUpdated(searchArray: JSONArray) {

    }

}

