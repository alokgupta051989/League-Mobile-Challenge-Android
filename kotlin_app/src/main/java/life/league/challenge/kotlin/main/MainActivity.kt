package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.feature.feed.FeedScreen
import life.league.challenge.feature.feed.FeedViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FeedScreen(viewModel = viewModel)
        }
    }
}
