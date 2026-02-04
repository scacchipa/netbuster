package ar.com.westsoft.netbuster.ui.screen.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.netbuster.usecase.GetAuthModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthModeUseCase: GetAuthModeUseCase,
) : ViewModel() {

    private val _navigationEvent = Channel<Boolean>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun startSplash() {
        viewModelScope.launch {
            delay(3000)
            _navigationEvent.send(getAuthModeUseCase())
        }
    }
}