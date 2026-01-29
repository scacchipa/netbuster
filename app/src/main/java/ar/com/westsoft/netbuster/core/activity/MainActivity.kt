package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import ar.com.westsoft.netbuster.core.ext.map
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : FragmentActivity() {

    var tvAPIClient: TvAPIClient? = null
    var favoriteSeriesList = emptyList<Series>()
//    var favoriteSeriesAdapter: SeriesAdapter? = null

    var searchFragment: SearchFragment? = null
    var posterFragment: PosterFragment? = null
    var episodeFragment: EpisodeFragment? = null
    var configFragment: ConfigFragment? = null
    var favoriteFragment: FavoriteFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAPIClient = TvAPIClient(this)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        searchFragment = SearchFragment(this)
        posterFragment = PosterFragment(this)
        episodeFragment = EpisodeFragment(this)
        configFragment = ConfigFragment(this)
        favoriteFragment = FavoriteFragment(this).apply {
            favoriteSeriesList = JSONArray(PreferenceManager.getDefaultSharedPreferences(baseContext)
                .getString("favoriteSeries", "[]"))
                .map { Series.fromJson(it) }
            updateFavoriteSeriesList()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, searchFragment!!,"Initial Fragment")
            }
        }

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        if (!searchFragment!!.isVisible)
                            replace(R.id.fragment_container_view, searchFragment!!, "Poster Fragment")
                    }
                    true
                }
                R.id.nav_favorite -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        if (!favoriteFragment!!.isVisible)
                            replace(R.id.fragment_container_view, favoriteFragment!!,"Poster Fragment")
                    }
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        if (!configFragment!!.isVisible)
                            replace(R.id.fragment_container_view, configFragment!!, "Config Fragment")
                    }
                    true
                }
                else -> super.onOptionsItemSelected(menuItem)
            }
        }

    }
    fun showSeriesPoster(series: Series) {
        posterFragment?.series = series
        showSeriesPoster()
    }
    fun showSeriesPoster(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, posterFragment!!,"Poster Fragment")
        }
    }
    fun showEpisodeInfo(jsonObject: JSONObject, seriesTitle: String) {
        episodeFragment?.setInfo(jsonObject, seriesTitle)
        supportFragmentManager. commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, episodeFragment!!)
        }
    }

    fun toggleFavorite(series: Series): List<Series> {
        val isFavorite = this.favoriteSeriesList.contains(series)
        return if (isFavorite) {
            this.removeFromFavoriteArray(series)
        } else {
            this.appendToFavoriteArray(series)
        }
    }

    fun appendToFavoriteArray(series: Series): List<Series> {
        favoriteSeriesList += series
        return favoriteSeriesList
    }
    fun removeFromFavoriteArray(series: Series): List<Series> {
        favoriteSeriesList -= series
        return favoriteSeriesList
    }
}