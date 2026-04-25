package life.league.challenge.core.domain.usecase

import life.league.challenge.core.domain.repository.PostRepository
import javax.inject.Inject

class SyncPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke() {
        repository.syncPosts()
    }
}
