package life.league.challenge.kotlin

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import life.league.challenge.kotlin.domain.GetPostsUseCase
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.ui.FeedUiState
import life.league.challenge.kotlin.ui.FeedViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: FeedViewModel
    private lateinit var mockGetPostsUseCase: GetPostsUseCase
    private lateinit var mockedLog: MockedStatic<Log>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockedLog = mockStatic(Log::class.java)
        mockGetPostsUseCase = mock()
        viewModel = FeedViewModel(mockGetPostsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockedLog.close()
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(FeedUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts success updates state to Success`() = runTest {
        val posts = listOf(Post(1, "user", "url", "title", "desc"))
        whenever(mockGetPostsUseCase.invoke()).thenReturn(posts)

        viewModel.fetchPosts()

        assertEquals(FeedUiState.Success(posts), viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts error updates state to Error`() = runTest {
        whenever(mockGetPostsUseCase.invoke()).thenThrow(RuntimeException("Test error"))

        viewModel.fetchPosts()

        assertTrue(viewModel.uiState.value is FeedUiState.Error)
        assertEquals("Test error", (viewModel.uiState.value as FeedUiState.Error).message)
    }

    @Test
    fun `refresh updates isRefreshing state correctly`() = runTest {
        whenever(mockGetPostsUseCase.invoke()).thenReturn(emptyList())

        viewModel.fetchPosts(isRefresh = true)

        assertEquals(false, viewModel.isRefreshing.value)
        assertTrue(viewModel.uiState.value is FeedUiState.Success)
    }
}
