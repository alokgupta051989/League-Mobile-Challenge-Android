package life.league.challenge.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.ui.FeedUiState
import life.league.challenge.kotlin.ui.FeedViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: FeedViewModel
    private lateinit var repository: FakeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeRepository()
        viewModel = FeedViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(FeedUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts success updates state to Success`() = runTest {
        val posts = listOf(Post(1, "user", "url", "title", "desc"))
        repository.postsToReturn = posts

        viewModel.fetchPosts()

        assertEquals(FeedUiState.Success(posts), viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts error updates state to Error`() = runTest {
        repository.shouldThrowError = true

        viewModel.fetchPosts()

        assertTrue(viewModel.uiState.value is FeedUiState.Error)
    }

    private class FakeRepository : Repository(api = object : life.league.challenge.kotlin.api.Api {
        override suspend fun login(credentials: String?) = throw NotImplementedError()
        override suspend fun getUsers(token: String) = throw NotImplementedError()
        override suspend fun getPosts(token: String) = throw NotImplementedError()
    }) {
        var postsToReturn: List<Post> = emptyList()
        var shouldThrowError = false

        override suspend fun getPosts(username: String, password: String): List<Post> {
            if (shouldThrowError) throw Exception("Test error")
            return postsToReturn
        }
    }
}
