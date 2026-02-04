package ar.com.westsoft.netbuster.data.repository

import ar.com.westsoft.netbuster.data.source.LocalStore
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    val localStore: LocalStore
) {
    fun getAuthMode(): Boolean = localStore.retrieveAuthMode()

    fun storeAuthConfig(authMode: Boolean, password: String) {
        localStore.storeAuthConfig(authMode, password)
    }
}