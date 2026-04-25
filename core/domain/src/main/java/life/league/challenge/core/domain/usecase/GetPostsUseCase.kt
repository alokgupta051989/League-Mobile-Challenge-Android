package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}
