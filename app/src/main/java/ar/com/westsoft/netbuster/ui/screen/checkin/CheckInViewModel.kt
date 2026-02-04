package ar.com.westsoft.netbuster.ui.screen.checkin

import androidx.lifecycle.ViewModel
import ar.com.westsoft.netbuster.data.repository.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val authenticator: Authenticator,
) : ViewModel() {

    fun checkBiometricDeviceIsOk() = authenticator.checkBiometricDeviceIsOk()

    fun checkPassword(password: String) = authenticator.checkPassword(password)
}