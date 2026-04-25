package life.league.challenge.core.domain.usecase

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
    private lateinit var mockRepository: PostRepository

    @Before
    fun setup() {
        mockRepository = mock()
        useCase = GetPostsUseCase(mockRepository)
    }

    @Test
    fun `invoke calls repository and returns posts`() = runTest {
        val posts = listOf(Post(1, "user", "url", "title", "desc"))
        whenever(mockRepository.getPosts("hello", "world")).thenReturn(posts)

        val result = useCase()

        assertEquals(posts, result)
    }
}
