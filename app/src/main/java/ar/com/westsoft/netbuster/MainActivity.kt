package ar.com.westsoft.netbuster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    var tvAPIClient: TvAPIClient? = null
    var serieArray = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvAPIClient = TvAPIClient(this)

        val bottonNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val fragContainer = findViewById<FragmentContainerView>(R.id.fragment_container_view)
        val firstFragment = FirstFragment()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, firstFragment,"Initial Fragment")
            }
        }


        println("Pre runBlcking")
        GlobalScope.launch { serieArray = tvAPIClient?.getSyncArrayJsonResponse()?:serieArray }
        println(serieArray.length())
        println("Post runBlocking")

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
}