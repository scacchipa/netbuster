package ar.com.westsoft.netbuster.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONObject

class FavoriteViewModel: ViewModel() {
    val favoriteSerieArray = MutableLiveData<JSONArray>()
    fun onAddSerieToFavorite(serie: JSONObject) {

    }
    fun onFavoriteArrayWasUpdated(favoriteArray: JSONArray) {

    }
}