package com.example.st_nobrainerquizapp.otherGames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.st_nobrainerquizapp.otherGames.ui.theme.Snoke_game1Theme

class snake_game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Snoke_game1Theme {
                val viewModel = viewModel<SnakeGameViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                SnakeGameScreen(
                    state = state,
                    onEvent = { event ->
                        when (event) {
                            is SnakeGameEvent.ExitGame -> exitGame()
                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
        }
    }

    private fun exitGame() {
        finish()
    }
}