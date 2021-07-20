package ar.com.westsoft.netbuster.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ar.com.westsoft.netbuster.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var tvAPIClient: TvAPIClient? = null
    var serieArray = JSONArray()

    var searchFragment: SearchFragment? = null
    var posterFragment: PosterFragment? = null
    var episodeFragment: EpisodeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvAPIClient = TvAPIClient(this)

        val bottonNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        searchFragment = SearchFragment(this)
        posterFragment = PosterFragment(this)
        episodeFragment = EpisodeFragment(this)
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
                        replace(R.id.fragment_container_view, searchFragment!!,"Poster Fragment")
                    }
                    true
                }
                R.id.nav_favority -> {
                    println("nav_favority was selected")
                    true
                }
                R.id.nav_settings -> {
                    print("nav_setting was selected")
                    true
                }
                else -> super.onOptionsItemSelected(menuItem)
            }
        }
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
}