package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostsUseCaseTest {

    private lateinit var useCase: GetPostsUseCase
    private lateinit var repository: PostRepository

    @Before
    fun setup() {
        repository = mock()
        useCase = GetPostsUseCase(repository)
    }

    @Test
    fun `invoke returns flow of posts from repository`() = runTest {
        val posts = listOf(Post(1, "user", "avatar", "title", "body"))
        whenever(repository.getPosts()).thenReturn(flowOf(posts))

        val result = useCase().first()

        assertEquals(1, result.size)
        assertEquals(posts, result)
    }
}
