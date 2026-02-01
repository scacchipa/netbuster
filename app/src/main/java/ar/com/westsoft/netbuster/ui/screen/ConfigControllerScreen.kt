package ar.com.westsoft.netbuster.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ConfigControllerScreen(
    configVM: ConfigViewModel = hiltViewModel(),
    context: Context,
) {
    ConfigScreen(
        initialAuthMode = configVM.getAuthMode(),
        onSavePushed = { authMode, password ->
            configVM.saveAuthConfig(authMode, password)
            val toastText =
                if (authMode) "The password was stored"
                else "The password was removed"
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT,).show()
        }
    )
}