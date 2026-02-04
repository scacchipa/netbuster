package ar.com.westsoft.netbuster.component

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.com.westsoft.netbuster.ui.screen.splashscreen.SplashScreen
import ar.com.westsoft.netbuster.ui.screen.splashscreen.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashVM: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
        lifecycleScope.launch {
            splashVM.navigationEvent.collect { authMode ->
                if (authMode) {
                    startActivity(Intent(baseContext, CheckingActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(baseContext, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}