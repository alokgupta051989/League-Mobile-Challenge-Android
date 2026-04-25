package life.league.challenge.core.domain.usecase

import life.league.challenge.core.domain.repository.PostRepository
import javax.inject.Inject

/**
 * Use case to synchronize local post data with the remote API.
 * It fetches the latest posts and users, performs mapping, and updates the local database.
 */
class SyncPostsUseCase @Inject constructor(private val repository: PostRepository) {
    /**
     * Triggers the synchronization process.
     */
    suspend operator fun invoke() {
        repository.syncPosts()
    }
}
