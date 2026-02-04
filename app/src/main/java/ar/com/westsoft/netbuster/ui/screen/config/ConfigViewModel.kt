package ar.com.westsoft.netbuster.ui.screen.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.di.DefaultDispatcher
import ar.com.westsoft.netbuster.usecase.GetAuthModeUseCase
import ar.com.westsoft.netbuster.usecase.StoreAuthConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getAuthModeUseCase: GetAuthModeUseCase,
    private val storeAuthConfigUseCase: StoreAuthConfigUseCase,
    @param:DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {
    fun getAuthMode(): Boolean = getAuthModeUseCase()

    fun saveAuthConfig(authMode: Boolean, password: String) {
        viewModelScope.launch(defaultDispatcher) {
            storeAuthConfigUseCase(authMode, password)
        }
    }
}