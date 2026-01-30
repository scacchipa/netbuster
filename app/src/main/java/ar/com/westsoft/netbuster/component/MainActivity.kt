package ar.com.westsoft.netbuster.component

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import ar.com.westsoft.netbuster.data.type.Episode
import ar.com.westsoft.netbuster.data.type.Series
import ar.com.westsoft.netbuster.fragment.ConfigFragment
import ar.com.westsoft.netbuster.fragment.EpisodeFragment
import ar.com.westsoft.netbuster.fragment.EpisodeScreenViewModel
import ar.com.westsoft.netbuster.fragment.FavoriteFragment
import ar.com.westsoft.netbuster.fragment.PosterFragment
import ar.com.westsoft.netbuster.fragment.PosterViewModel
import ar.com.westsoft.netbuster.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    var tvAPIClient: TvAPIClient? = null

    val posterViewModel by viewModels<PosterViewModel>()
    val episodeVM by viewModels<EpisodeScreenViewModel>()

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
        favoriteFragment = FavoriteFragment(this)
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
        posterViewModel.setSeries(series)
        showSeriesPoster()
    }
    fun showSeriesPoster(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, posterFragment!!,"Poster Fragment")
        }
    }
    fun showEpisodeInfo(episode: Episode) {

        episodeVM.setEpisode(episode)
        supportFragmentManager. commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, episodeFragment!!)
        }
    }
}