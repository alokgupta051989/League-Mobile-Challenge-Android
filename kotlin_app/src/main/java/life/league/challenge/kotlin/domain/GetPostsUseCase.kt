package life.league.challenge.kotlin.domain

import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): List<Post> {
        return repository.getPosts("hello", "world")
    }
}
