package ar.com.westsoft.netbuster.data.repository

import android.content.Context
import androidx.biometric.BiometricManager
import ar.com.westsoft.netbuster.data.source.LocalStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Authenticator @Inject constructor(
    private val localStore: LocalStore,
    @param:ApplicationContext val context: Context
) {
    fun checkBiometricDeviceIsOk(): Boolean {
        val biometric = BiometricManager.from(context).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        return when (biometric) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    fun checkPassword(password: String): Boolean {
        return password == localStore.retrievePassword()
    }
}