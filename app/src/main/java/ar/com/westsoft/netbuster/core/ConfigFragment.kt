package ar.com.westsoft.netbuster.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R

class ConfigFragment(val callback: MainActivity) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.config_layout, container, false)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(callback.baseContext)

        val authModeCB = rootView.findViewById<CheckBox>(R.id.auth_mode)
        authModeCB.isChecked = sharedPref?.getBoolean("authMode", false)?:false
        authModeCB.setOnCheckedChangeListener { checkButton, isChecked ->

            val biometricManager = BiometricManager.from(callback)
            when (biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS ->
                    Toast.makeText(callback,  "You can authenticate using biometrics.", Toast.LENGTH_SHORT)
                        .show()
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    Toast.makeText(callback, "No biometric features available on this device.", Toast.LENGTH_SHORT)
                        .show()
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    Toast.makeText(callback, "Biometric features are currently unavailable.", Toast.LENGTH_SHORT)
                        .show()
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Toast.makeText(callback, "Authentication failed. You are not enrolled",
                        Toast.LENGTH_LONG)
                        .show()
                    checkButton.isChecked = false
                }
            }

            with (sharedPref?.edit()) {
                this?.putBoolean("authMode", checkButton.isChecked)
                this?.commit()
            }
        }

        return rootView
    }
}