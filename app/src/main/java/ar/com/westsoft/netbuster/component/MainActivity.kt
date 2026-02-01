package ar.com.westsoft.netbuster.component

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import ar.com.westsoft.netbuster.data.source.TvAPIClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    var tvAPIClient: TvAPIClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainController()
        }
    }
}