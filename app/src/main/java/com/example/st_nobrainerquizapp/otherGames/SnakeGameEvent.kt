package com.example.st_nobrainerquizapp.otherGames

import androidx.compose.ui.geometry.Offset

sealed class SnakeGameEvent {
    data object StartGame : SnakeGameEvent()
    data object PauseGame : SnakeGameEvent()
    data object ResetGame : SnakeGameEvent()
    data class UpdateDirection(val offset: Offset, val canvasWidth: Int) : SnakeGameEvent()
    data object ExitGame : SnakeGameEvent()
}