package ar.com.westsoft.netbuster.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R
import ar.com.westsoft.netbuster.core.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)
        GlobalScope.launch {
            delay(5000)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(baseContext)

            if (sharedPref?.getBoolean("authMode", false) == true)
                startActivity(Intent(baseContext, CheckingActivity::class.java))
            else
                startActivity(Intent(baseContext, MainActivity::class.java))
        }
    }
}
