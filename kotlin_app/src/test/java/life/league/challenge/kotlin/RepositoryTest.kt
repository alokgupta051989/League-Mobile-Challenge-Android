package life.league.challenge.kotlin

import android.util.Base64
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import life.league.challenge.kotlin.api.Api
import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.api.login
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.PostDto
import life.league.challenge.kotlin.model.UserDto
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {

    private lateinit var repository: Repository
    private val mockApi: Api = mock()
    private lateinit var mockedBase64: MockedStatic<Base64>

    @Before
    fun setup() {
        mockedBase64 = mockStatic(Base64::class.java)
        mockedBase64.`when`<String> { 
            Base64.encodeToString(any(), any()) 
        }.thenReturn("encoded_string")
        
        repository = Repository(mockApi)
    }

    @After
    fun tearDown() {
        mockedBase64.close()
    }

    @Test
    fun `getPosts successfully logs in and fetches posts`() = runTest {
        // Arrange
        val account = Account(apiKey = "test_token")
        val users = listOf(UserDto(id = 1, name = "User", username = "username", avatar = "url"))
        val posts = listOf(PostDto(userId = 1, id = 101, title = "Title", body = "Body"))

        whenever(mockApi.login(any<String>())).thenReturn(account)
        whenever(mockApi.getUsers("test_token")).thenReturn(users)
        whenever(mockApi.getPosts("test_token")).thenReturn(posts)

        // Act
        val result = repository.getPosts("user", "pass")

        // Assert
        assertEquals(1, result.size)
        assertEquals("username", result[0].username)
        assertEquals(101, result[0].id)
    }

    @Test(expected = Exception::class)
    fun `getPosts throws exception when login fails`() = runTest {
        // Arrange
        whenever(mockApi.login(any<String>())).thenReturn(Account(apiKey = null))

        // Act
        repository.getPosts("user", "pass")
    }
}
