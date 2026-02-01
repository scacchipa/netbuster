package ar.com.westsoft.netbuster.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.ext.map
import ar.com.westsoft.netbuster.ext.toJSONArray
import ar.com.westsoft.netbuster.ext.toMap
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
    val favoriteSharedPref: SharedPreferences =
        context.getSharedPreferences("favoriteSeries", Context.MODE_PRIVATE)

    val configSharedPref: SharedPreferences =
        context.getSharedPreferences("config", Context.MODE_PRIVATE)

    private val _favoriteSeriesSF = MutableStateFlow<Map<Int, Series>>(
        retrieveFavoriteSeries()
    )

    val favoriteSeriesSF: StateFlow<Map<Int,Series>> = _favoriteSeriesSF

    fun retrieveFavoriteSeries(): Map<Int,Series> = JSONArray(favoriteSharedPref.getString("favoriteSeries", "[]"))
        .map { Series.fromJson(it) }
        .toMap()

    suspend fun storeFavoriteSeriesList(seriesList: Map<Int, Series>) {
        favoriteSharedPref.edit {
            putString("favoriteSeries", seriesList.values.toList().toJSONArray().toString())
        }
        _favoriteSeriesSF.emit(seriesList)
    }

    suspend fun addFavoriteSeries(series: Series) {
        storeFavoriteSeriesList(_favoriteSeriesSF.value + (series.id to series))
    }

    suspend fun removeFavoriteSeries(series: Series) {
        storeFavoriteSeriesList(_favoriteSeriesSF.value - (series.id))
    }

    suspend fun toggleFavorite(series: Series) {
        if (_favoriteSeriesSF.value[series.id] != null) {
            removeFavoriteSeries(series)
        } else {
            addFavoriteSeries(series)
        }
    }

    fun retrieveAuthMode(): Boolean = configSharedPref.getBoolean("authMode", false)

    fun retrievePassword(): String = configSharedPref.getString("password", "") ?: ""

    fun storeAuthConfig(authMode: Boolean, password: String) {
        configSharedPref.edit {
            if (authMode) {
                putBoolean("authMode", true)
                putString("password", password)
            } else {
                putBoolean("authMode", false)
            }
        }
    }
}
