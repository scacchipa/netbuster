package ar.com.westsoft.netbuster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottonNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

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

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }
    }
}