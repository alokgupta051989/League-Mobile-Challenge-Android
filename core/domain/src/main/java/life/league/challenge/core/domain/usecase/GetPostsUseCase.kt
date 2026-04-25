package life.league.challenge.core.domain.usecase

import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(): List<Post> {
        return repository.getPosts("hello", "world")
    }
}
