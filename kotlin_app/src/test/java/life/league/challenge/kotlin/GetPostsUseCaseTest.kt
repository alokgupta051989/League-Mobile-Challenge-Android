package life.league.challenge.kotlin

import kotlinx.coroutines.test.runTest
import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.domain.GetPostsUseCase
import life.league.challenge.kotlin.model.Post
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostsUseCaseTest {

    private lateinit var useCase: GetPostsUseCase
    private lateinit var mockRepository: Repository

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
