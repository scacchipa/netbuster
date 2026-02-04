package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.repository.Authenticator
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    operator fun invoke(password: String) = authenticator.checkPassword(password)
}