package ar.com.westsoft.netbuster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var tvAPIClient: TvAPIClient? = null
    var serieArray = JSONArray()

    var searchFragment: SearchFragment? = null
    var posterFragment: PosterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvAPIClient = TvAPIClient(this)

        val bottonNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        searchFragment = SearchFragment(this)
        posterFragment = PosterFragment(this)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, searchFragment!!,"Initial Fragment")
            }
        }

        bottonNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    println("nav_home as selected")
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
    fun showPoster(jsonObject: JSONObject){
        posterFragment?.setSerie(jsonObject)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, posterFragment!!,"Poster Fragment")
        }
    }
}