package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.test.runTest
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
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
    fun `invoke performs login when not logged in`() = runTest {
        val posts = listOf(Post(1, "user", "avatar", "title", "body"))
        whenever(repository.isLoggedIn()).thenReturn(false)
        whenever(repository.getPosts()).thenReturn(posts)

        val result = useCase()

        verify(repository).login("hello", "world")
        assertEquals(1, result.size)
    }

    @Test
    fun `invoke skips login when already logged in`() = runTest {
        val posts = listOf(Post(1, "user", "avatar", "title", "body"))
        whenever(repository.isLoggedIn()).thenReturn(true)
        whenever(repository.getPosts()).thenReturn(posts)

        val result = useCase()

        verify(repository, org.mockito.Mockito.never()).login(anyString(), anyString())
        assertEquals(1, result.size)
    }
}

private fun anyString(): String = org.mockito.ArgumentMatchers.anyString()
