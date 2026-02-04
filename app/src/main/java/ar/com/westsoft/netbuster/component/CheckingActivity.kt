package ar.com.westsoft.netbuster.component

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import ar.com.westsoft.netbuster.ui.screen.checkin.CheckInScreen
import ar.com.westsoft.netbuster.ui.screen.checkin.CheckInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckingActivity : AppCompatActivity() {
    val checkInVM by viewModels<CheckInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var password by remember { mutableStateOf("") }

            val biometricPrompt = BiometricPrompt(
                this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT
                        )
                            .show()
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

            Scaffold {
                Box(
                    modifier = Modifier.padding(it)
                ) {
                    CheckInScreen(
                        password = password,
                        onPasswordChanged = { newPassword -> password = newPassword },
                        checkBiometricDeviceIsOk = checkInVM.checkBiometricDeviceIsOk(),
                        onSignInClick = {
                            if (checkInVM.checkPassword(password)) {
                                this@CheckingActivity.goToMainActivity()
                            } else {
                                Toast.makeText(
                                    applicationContext, "Password error",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        onBiometricClick = {
                            biometricPrompt.authenticate(promptInfo)
                        },
                    )
                }
            }
        }
    }

    fun goToMainActivity() {
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }
}

