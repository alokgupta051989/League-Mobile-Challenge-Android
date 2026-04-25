package life.league.challenge.feature.feed

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import life.league.challenge.core.domain.usecase.GetPostsUseCase
import life.league.challenge.core.domain.usecase.SyncPostsUseCase
import life.league.challenge.core.model.Post
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
    private lateinit var mockGetPostsUseCase: GetPostsUseCase
    private lateinit var mockSyncPostsUseCase: SyncPostsUseCase
    private lateinit var mockedLog: MockedStatic<Log>
    private val postsFlow = MutableStateFlow<List<Post>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockedLog = mockStatic(Log::class.java)
        mockGetPostsUseCase = mock()
        mockSyncPostsUseCase = mock()
        
        postsFlow.value = emptyList()
        whenever(mockGetPostsUseCase.invoke()).thenReturn(postsFlow)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockedLog.close()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val viewModel = FeedViewModel(mockGetPostsUseCase, mockSyncPostsUseCase)
        assertEquals(FeedUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts success updates state to Success`() = runTest {
        val posts = listOf(Post(1, "user", "url", "title", "desc"))
        
        whenever(mockSyncPostsUseCase.invoke()).thenAnswer {
            postsFlow.value = posts
            Unit
        }

        val viewModel = FeedViewModel(mockGetPostsUseCase, mockSyncPostsUseCase)

        assertEquals(FeedUiState.Success(posts), viewModel.uiState.value)
    }

    @Test
    fun `fetchPosts error updates state to Error`() = runTest {
        whenever(mockSyncPostsUseCase.invoke()).thenThrow(RuntimeException("Test error"))

        val viewModel = FeedViewModel(mockGetPostsUseCase, mockSyncPostsUseCase)

        assertTrue(viewModel.uiState.value is FeedUiState.Error)
        assertEquals("Test error", (viewModel.uiState.value as FeedUiState.Error).message)
    }

    @Test
    fun `refresh updates isRefreshing state correctly`() = runTest {
        whenever(mockSyncPostsUseCase.invoke()).thenReturn(Unit)

        val viewModel = FeedViewModel(mockGetPostsUseCase, mockSyncPostsUseCase)
        
        viewModel.fetchPosts(isRefresh = true)

        assertEquals(false, viewModel.isRefreshing.value)
    }
}
