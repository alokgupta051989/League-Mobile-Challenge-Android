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
    fun `getPosts success with login`() = runTest {
        val account = Account(apiKey = "token")
        val users = listOf(UserDto(1, "name", "user", "avatar"))
        val posts = listOf(PostDto(1, 101, "title", "body"))

        whenever(mockTokenManager.getToken()).thenReturn(null)
        whenever(Base64.encodeToString(any(), any())).thenReturn("encoded")
        whenever(mockApi.login("Basic encoded")).thenReturn(account)
        whenever(mockApi.getUsers()).thenReturn(users)
        whenever(mockApi.getPosts()).thenReturn(posts)

        val result = repository.getPosts("user", "pass")

        assertEquals(1, result.size)
        assertEquals("user", result[0].username)
    }

    @Test
    fun `getPosts success with cached token`() = runTest {
        val users = listOf(UserDto(1, "name", "user", "avatar"))
        val posts = listOf(PostDto(1, 101, "title", "body"))

        whenever(mockTokenManager.getToken()).thenReturn("cached_token")
        whenever(mockApi.getUsers()).thenReturn(users)
        whenever(mockApi.getPosts()).thenReturn(posts)

        val result = repository.getPosts("user", "pass")

        assertEquals(1, result.size)
        assertEquals("user", result[0].username)
    }

    @Test
    fun `getPosts failure on login throws exception`() = runTest {
        whenever(mockTokenManager.getToken()).thenReturn(null)
        whenever(Base64.encodeToString(any(), any())).thenReturn("encoded")
        whenever(mockApi.login("Basic encoded")).thenThrow(RuntimeException("Login Failed"))

        assertThrows(RuntimeException::class.java) {
            runBlocking {
                repository.getPosts("user", "pass")
            }
        }
    }

    @Test
    fun `getPosts failure on empty api key throws exception`() = runTest {
        val account = Account(apiKey = null)
        whenever(mockTokenManager.getToken()).thenReturn(null)
        whenever(Base64.encodeToString(any(), any())).thenReturn("encoded")
        whenever(mockApi.login("Basic encoded")).thenReturn(account)

        val exception = assertThrows(Exception::class.java) {
            runBlocking {
                repository.getPosts("user", "pass")
            }
        }
        assertEquals("Login failed: No API key returned", exception.message)
    }
}

private fun <T> runBlocking(block: suspend () -> T): T = kotlinx.coroutines.runBlocking { block() }
