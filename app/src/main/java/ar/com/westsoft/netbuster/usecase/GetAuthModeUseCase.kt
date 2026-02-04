package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.ConfigRepository
import javax.inject.Inject

class GetAuthModeUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    operator fun invoke(): Boolean = configRepository.getAuthMode()
}