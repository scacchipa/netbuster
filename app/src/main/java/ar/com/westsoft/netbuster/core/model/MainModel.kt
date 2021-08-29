package ar.com.westsoft.netbuster.core.model

import androidx.lifecycle.MutableLiveData
import ar.com.westsoft.netbuster.core.activity.MainActivity
import org.json.JSONArray

class MainModel(val mainActivity: MainActivity) {

    var searchJsonArray = MutableLiveData(JSONArray())
    var favoriteJsonArray = MutableLiveData(JSONArray())

    init {
        searchJsonArray.observe(mainActivity, {
            mainActivity.searchViewModel.onSearchArrayWasUpdated(it)
        })
        favoriteJsonArray.observe(mainActivity,{
            mainActivity.searchViewModel.onFavoriteArrayWasUpdated(it)
        })
    }

    fun getSearchSeries() = searchJsonArray
    fun getfavoriteJson() = favoriteJsonArray
}
