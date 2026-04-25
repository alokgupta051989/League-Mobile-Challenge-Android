package life.league.challenge.core.data

import android.util.Base64
import android.util.Log
import kotlinx.coroutines.test.runTest
import life.league.challenge.core.model.Account
import life.league.challenge.core.network.api.Api
import life.league.challenge.core.network.model.PostDto
import life.league.challenge.core.network.model.UserDto
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RepositoryTest {

    private lateinit var repository: Repository
    private lateinit var mockApi: Api
    private lateinit var mockTokenManager: TokenManager
    private lateinit var mockedLog: MockedStatic<Log>
    private lateinit var mockedBase64: MockedStatic<Base64>

    @Before
    fun setup() {
        mockedLog = mockStatic(Log::class.java)
        mockedBase64 = mockStatic(Base64::class.java)
        mockApi = mock()
        mockTokenManager = mock()
        repository = Repository(mockApi, mockTokenManager)
    }

    @After
    fun tearDown() {
        mockedLog.close()
        mockedBase64.close()
    }

    @Test
    fun `login success saves token`() = runTest {
        val account = Account(apiKey = "token")
        whenever(Base64.encodeToString(any(), any())).thenReturn("encoded")
        whenever(mockApi.login("Basic encoded")).thenReturn(account)

        repository.login("user", "pass")

        verify(mockTokenManager).saveToken("token")
    }

    @Test
    fun `login failure with empty api key throws exception`() = runTest {
        val account = Account(apiKey = null)
        whenever(Base64.encodeToString(any(), any())).thenReturn("encoded")
        whenever(mockApi.login("Basic encoded")).thenReturn(account)

        val exception = assertThrows(Exception::class.java) {
            runBlocking { repository.login("user", "pass") }
        }
        assertEquals("Login failed: No API key returned", exception.message)
    }

    @Test
    fun `getPosts returns mapped domain models`() = runTest {
        val users = listOf(UserDto(1, "name", "user", "avatar"))
        val posts = listOf(PostDto(1, 1, "title", "body"))

        whenever(mockApi.getUsers()).thenReturn(users)
        whenever(mockApi.getPosts()).thenReturn(posts)

        val result = repository.getPosts()

        assertEquals(1, result.size)
        assertEquals("user", result[0].username)
        assertEquals("title", result[0].title)
    }

    @Test
    fun `isLoggedIn returns true when token exists`() = runTest {
        whenever(mockTokenManager.getToken()).thenReturn("token")
        val result = repository.isLoggedIn()
        assertEquals(true, result)
    }

    @Test
    fun `logout clears token`() = runTest {
        repository.logout()
        verify(mockTokenManager).clearToken()
    }
}

private fun <T> runBlocking(block: suspend () -> T): T = kotlinx.coroutines.runBlocking { block() }
