package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.ConfigRepository
import javax.inject.Inject

class StoreAuthConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(authMode: Boolean, password: String) {
        configRepository.storeAuthConfig(authMode, password)
    }
}