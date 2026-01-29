package ar.com.westsoft.netbuster.component

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class CheckingActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checking_view)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val biometricLoginButton = findViewById<ImageButton>(R.id.fingetButton)
        val checkPIN = findViewById<Button>(R.id.check_pin)
        val passwordTE = findViewById<EditText>(R.id.password)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    if (sharedPref?.getString("password", null) == passwordTE.text.toString()) {
                        goToMainActivity()
                    } else Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    goToMainActivity()
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

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for NetBuster")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricLoginButton.isEnabled = checkBiometricDeviceIsOk()

        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        checkPIN.setOnClickListener {
            if (sharedPref?.getString("password", null) == passwordTE.text.toString())
                goToMainActivity()
        }
    }
    fun goToMainActivity() {
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }
    fun checkBiometricDeviceIsOk() : Boolean{
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> return true
            else ->return false
        }
    }

}