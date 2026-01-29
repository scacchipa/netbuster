package ar.com.westsoft.netbuster.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.component.MainActivity
import ar.com.westsoft.netbuster.ui.screen.ConfigScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigFragment(val callback: MainActivity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(callback.baseContext)

        val composedView = ComposeView(requireContext()).apply {
            setContent {
                ConfigScreen(
                    initialAuthMode = sharedPref?.getBoolean("authMode", false) ?: false,
                    onSavePushed = { authMode, password ->
                        sharedPref.edit {
                            if (authMode) {
                                putBoolean("authMode", true)
                                putString("password", password)
                            } else {
                                putBoolean("authMode", false)
                            }
                        }
                        val toastText =
                            if (authMode) "The password was stored"
                            else "The password was removed"
                        Toast.makeText(callback, toastText, Toast.LENGTH_SHORT,).show()
                    }
                )
            }
        }
        return composedView
    }
}