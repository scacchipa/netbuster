package ar.com.westsoft.netbuster.component

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)

        lifecycleScope.launch {
            delay(3000)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(baseContext)

            if (sharedPref?.getBoolean("authMode", false) == true) {
                startActivity(Intent(baseContext, CheckingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
        }
    }
}