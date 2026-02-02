package ar.com.westsoft.netbuster.ui.screen.config

import androidx.lifecycle.ViewModel
import ar.com.westsoft.netbuster.data.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    val configRepository: ConfigRepository
): ViewModel() {
    fun getAuthMode(): Boolean = configRepository.getAuthMode()

    fun getPassword(): String = configRepository.getPassword()

    fun saveAuthConfig(authMode: Boolean, password: String) {
        configRepository.storeAuthConfig(authMode, password)
    }
}