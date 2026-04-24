package life.league.challenge.kotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.model.Post

sealed class FeedUiState {
    object Loading : FeedUiState()
    data class Success(val posts: List<Post>) : FeedUiState()
    data class Error(val message: String) : FeedUiState()
}

class FeedViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    fun fetchPosts() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            try {
                // Using hardcoded credentials as per a typical challenge requirement 
                // unless specified otherwise. In a real app, these would come from a login screen.
                val posts = repository.getPosts("hello", "world")
                _uiState.value = FeedUiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value = FeedUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
