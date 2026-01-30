package ar.com.westsoft.netbuster.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ext.map
import ar.com.westsoft.netbuster.ext.toJSONArray
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStore @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favoriteSeries", Context.MODE_PRIVATE)

    private val _favoriteSeriesSF = MutableStateFlow<List<Series>>(
        retrieveFavoriteSeries()
    )

    val favoriteSeriesSF: StateFlow<List<Series>> = _favoriteSeriesSF

    fun retrieveFavoriteSeries(): List<Series> = JSONArray(sharedPreferences.getString("favoriteSeries", "[]"))
        .map { Series.fromJson(it) }

    suspend fun storeFavoriteSeriesList(seriesList: List<Series>) {
        sharedPreferences.edit {
            putString("favoriteSeries", seriesList.toJSONArray().toString())
        }
        _favoriteSeriesSF.emit(seriesList)
    }

    suspend fun addFavoriteSeries(series: Series) {
        storeFavoriteSeriesList(_favoriteSeriesSF.value + series)
    }

    suspend fun removeFavoriteSeries(series: Series) {
        storeFavoriteSeriesList(_favoriteSeriesSF.value - series)
    }

    suspend fun toggleFavorite(series: Series) {
        if (_favoriteSeriesSF.value.any { it.id == series.id }) {
            removeFavoriteSeries(series)
        } else {
            addFavoriteSeries(series)
        }
    }

}
