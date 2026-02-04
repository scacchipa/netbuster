package ar.com.westsoft.netbuster.ui.screen.checkin

import androidx.lifecycle.ViewModel
import ar.com.westsoft.netbuster.usecase.CheckBiometricDeviceIsOkUseCase
import ar.com.westsoft.netbuster.usecase.CheckPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val checkBiometricDeviceIsOkUseCase: CheckBiometricDeviceIsOkUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
) : ViewModel() {

    fun checkBiometricDeviceIsOk() = checkBiometricDeviceIsOkUseCase()

    fun checkPassword(password: String) = checkPasswordUseCase(password)
}