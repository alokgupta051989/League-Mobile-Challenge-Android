package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.api.Repository
import life.league.challenge.kotlin.ui.FeedScreen
import life.league.challenge.kotlin.ui.FeedViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manual DI for simplicity as per requirements
        val repository = Repository(Service.api)
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeedViewModel(repository) as T
            }
        }
        val viewModel = ViewModelProvider(this, viewModelFactory)[FeedViewModel::class.java]

        setContent {
            FeedScreen(viewModel = viewModel)
        }
    }
}
