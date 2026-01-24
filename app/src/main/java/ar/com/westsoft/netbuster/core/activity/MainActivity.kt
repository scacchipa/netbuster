package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.adapter.SeriesAdapter
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : FragmentActivity() {

    var tvAPIClient: TvAPIClient? = null
    var favoriteSerieArray = JSONArray()
    var favoriteSeriesAdapter: SeriesAdapter? = null

    var searchFragment: SearchFragment? = null
    var posterFragment: PosterFragment? = null
    var episodeFragment: EpisodeFragment? = null
    var configFragment: ConfigFragment? = null
    var favoriteFragment: FavoriteFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvAPIClient = TvAPIClient(this)

        val bottonNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        searchFragment = SearchFragment(this)
        posterFragment = PosterFragment(this)
        episodeFragment = EpisodeFragment(this)
        configFragment = ConfigFragment(this)
        favoriteFragment = FavoriteFragment(this)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, searchFragment!!,"Initial Fragment")
            }
        }

        bottonNav.setOnNavigationItemSelectedListener { menuItem ->
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
        favoriteSerieArray = JSONArray(PreferenceManager.getDefaultSharedPreferences(baseContext)
            .getString("favoriteSeries", "[]"))
    }
    fun showSeriePoster(jsonObject: JSONObject){
        posterFragment?.serieJsonObj = jsonObject
        showSeriePoster()
    }
    fun showSeriePoster(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, posterFragment!!,"Poster Fragment")
        }
    }
    fun showEpisodeInfo(jsonObject: JSONObject, serieTitle: String) {
        episodeFragment?.setInfo(jsonObject, serieTitle)
        supportFragmentManager. commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, episodeFragment!!)
        }
    }
    fun appendToFavoryArray( serieJsonObj: JSONObject) {
        favoriteSerieArray.put(serieJsonObj)
    }
    fun removeFromFavoriteArray(favoriteIdPos: Int) {
        favoriteSerieArray.remove(favoriteIdPos)
    }
}