package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import javax.inject.Inject

/**
 * Use case to retrieve a reactive stream of posts from the local database.
 * This stream will automatically emit new data whenever the local database is updated.
 */
class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    /**
     * Executes the use case and returns a flow of posts.
     */
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}
