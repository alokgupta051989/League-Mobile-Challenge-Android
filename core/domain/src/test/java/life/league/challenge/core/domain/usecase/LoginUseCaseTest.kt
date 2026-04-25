package life.league.challenge.core.domain.usecase

import kotlinx.coroutines.test.runTest
import life.league.challenge.core.domain.repository.PostRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LoginUseCaseTest {

    private lateinit var useCase: LoginUseCase
    private lateinit var repository: PostRepository

    @Before
    fun setup() {
        repository = mock()
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `invoke calls repository login`() = runTest {
        val username = "testUser"
        val password = "testPassword"

        useCase(username, password)

        verify(repository).login(username, password)
    }
}
