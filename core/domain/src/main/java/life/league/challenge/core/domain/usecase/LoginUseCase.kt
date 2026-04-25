package life.league.challenge.core.domain.usecase

import life.league.challenge.core.domain.repository.PostRepository
import javax.inject.Inject

/**
 * Use case to handle user authentication.
 * It takes credentials, performs login via the repository, and persists the resulting token.
 */
class LoginUseCase @Inject constructor(private val repository: PostRepository) {
    /**
     * Executes the login operation.
     * @param username The user's username.
     * @param password The user's password.
     */
    suspend operator fun invoke(username: String, password: String) {
        repository.login(username, password)
    }
}
