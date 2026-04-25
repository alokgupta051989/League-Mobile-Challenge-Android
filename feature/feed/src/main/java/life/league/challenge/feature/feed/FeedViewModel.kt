package life.league.challenge.feature.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import life.league.challenge.core.domain.usecase.GetPostsUseCase
import life.league.challenge.core.domain.usecase.SyncPostsUseCase
import life.league.challenge.core.model.Post
import javax.inject.Inject

sealed class FeedUiState {
    object Loading : FeedUiState()
    data class Success(val posts: List<Post>) : FeedUiState()
    data class Error(val message: String) : FeedUiState()
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val syncPostsUseCase: SyncPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        observePosts()
        fetchPosts()
    }

    private fun observePosts() {
        viewModelScope.launch {
            getPostsUseCase()
                .catch { e ->
                    _uiState.value = FeedUiState.Error(e.message ?: "Error observing database")
                }
                .collect { posts ->
                    if (posts.isNotEmpty()) {
                        _uiState.value = FeedUiState.Success(posts)
                    }
                }
        }
    }

    fun fetchPosts(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else if (_uiState.value !is FeedUiState.Success) {
                _uiState.value = FeedUiState.Loading
            }

            try {
                syncPostsUseCase()
                Log.d("FeedViewModel", "Sync success")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Error syncing posts", e)
                if (_uiState.value !is FeedUiState.Success) {
                    _uiState.value = FeedUiState.Error(e.message ?: "An unknown error occurred")
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
