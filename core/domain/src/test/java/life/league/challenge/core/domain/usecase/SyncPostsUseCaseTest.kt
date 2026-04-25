package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.test.runTest
import life.league.challenge.core.domain.repository.PostRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SyncPostsUseCaseTest {

    private lateinit var useCase: SyncPostsUseCase
    private lateinit var repository: PostRepository

    @Before
    fun setup() {
        repository = mock()
        useCase = SyncPostsUseCase(repository)
    }

    @Test
    fun `invoke calls repository syncPosts`() = runTest {
        useCase()

        verify(repository).syncPosts()
    }
}
