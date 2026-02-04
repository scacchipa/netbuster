package ar.com.westsoft.netbuster.component

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import ar.com.westsoft.netbuster.data.repository.Authenticator
import ar.com.westsoft.netbuster.data.repository.ConfigRepository
import ar.com.westsoft.netbuster.ui.screen.checkin.CheckInScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class CheckingActivity : AppCompatActivity() {
    val checkInVM by viewModels<CheckInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var password by remember { mutableStateOf("") }

            val executor = ContextCompat.getMainExecutor(this)
            val biometricPrompt = BiometricPrompt(
                this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        if (checkInVM.checkPassword(password)) {
                            this@CheckingActivity.goToMainActivity()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Authentication error: $errString", Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        this@CheckingActivity.goToMainActivity()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            applicationContext, "Authentication failed",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for NetBuster")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build()

            CheckInScreen(
                password = password,
                onPasswordChanged = { newPassword -> password = newPassword },
                checkBiometricDeviceIsOk = checkInVM.checkBiometricDeviceIsOk(),
                onSignInClick = {
                    checkInVM.checkPassword(password)
                },
                onBiometricClick = {
                    biometricPrompt.authenticate(promptInfo)
                },
            )
        }
    }

    fun goToMainActivity() {
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }
}

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val authenticator: Authenticator,
    @param:ApplicationContext val appContext: Context,
) : ViewModel() {

    fun checkBiometricDeviceIsOk() = authenticator.checkBiometricDeviceIsOk()

    fun checkPassword(password: String) = authenticator.checkPassword(password)

}