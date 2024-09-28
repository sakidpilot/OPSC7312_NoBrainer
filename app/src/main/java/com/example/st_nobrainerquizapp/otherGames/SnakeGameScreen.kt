package com.example.st_nobrainerquizapp.otherGames

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.st_nobrainerquizapp.R
import com.example.st_nobrainerquizapp.otherGames.ui.theme.Citrine
import com.example.st_nobrainerquizapp.otherGames.ui.theme.Custard
import com.example.st_nobrainerquizapp.otherGames.ui.theme.RoyalBlue

@Composable
fun SnakeGameScreen(
    state: SnakeGameState,
    onEvent: (SnakeGameEvent) -> Unit
) {
    val foodImageBitmap = ImageBitmap.imageResource(id = R.drawable.img_apple)
    val snakeHeadImageBitmap = when (state.direction) {
        Direction.RIGHT -> ImageBitmap.imageResource(id = R.drawable.img_snake_head)
        Direction.LEFT -> ImageBitmap.imageResource(id = R.drawable.img_snake_head2)
        Direction.UP -> ImageBitmap.imageResource(id = R.drawable.img_snake_head3)
        Direction.DOWN -> ImageBitmap.imageResource(id = R.drawable.img_snake_head4)
    }

    fun DrawScope.drawGameBoard(
        cellSize: Float,
        cellColor: Color,
        borderCellColor: Color,
        gridWidth: Int,
        gridHeight: Int
    ) {
        for (i in 0 until gridWidth) {
            for (j in 0 until gridHeight) {
                val isBorderCell = i == 0 || j == 0 || i == gridWidth - 1 || j == gridHeight - 1
                drawRect(
                    color = if (isBorderCell) borderCellColor
                    else if ((i + j) % 2 == 0) cellColor
                    else cellColor.copy(alpha = 0.5f),
                    topLeft = Offset(x = i * cellSize, y = j * cellSize),
                    size = Size(cellSize, cellSize)
                )
            }
        }
    }

    fun DrawScope.drawFood(
        foodImage: ImageBitmap,
        cellSize: Int,
        coordinate: Coordinate
    ) {
        drawImage(
            image = foodImage,
            dstOffset = IntOffset(
                x = (coordinate.x * cellSize),
                y = (coordinate.y * cellSize)
            ),
            dstSize = IntSize(cellSize, cellSize)
        )
    }

    fun DrawScope.drawSnake(
        snakeHeadImage: ImageBitmap,
        cellSize: Float,
        snake: List<Coordinate>
    ) {
        val cellSizeInt = cellSize.toInt()
        snake.forEachIndexed { index, coordinate ->
            val radius = if (index == snake.lastIndex) cellSize / 2.5f else cellSize / 2
            if (index == 0) {
                drawImage(
                    image = snakeHeadImage,
                    dstOffset = IntOffset(
                        x = (coordinate.x * cellSizeInt),
                        y = (coordinate.y * cellSizeInt)
                    ),
                    dstSize = IntSize(cellSizeInt, cellSizeInt)
                )
            } else {
                drawCircle(
                    color = Citrine,
                    center = Offset(
                        x = (coordinate.x * cellSize) + radius,
                        y = (coordinate.y * cellSize) + radius
                    ),
                    radius = radius
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Score and buttons at the top
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Score: ${state.snake.size - 1}",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onEvent(SnakeGameEvent.ResetGame) },
                            enabled = state.gameState == GameState.PAUSED || state.isGameOver
                        ) {
                            Text(text = if (state.isGameOver) "Reset" else "New Game")
                        }
                        Button(
                            onClick = {
                                when (state.gameState) {
                                    GameState.IDLE, GameState.PAUSED -> onEvent(SnakeGameEvent.StartGame)
                                    GameState.STARTED -> onEvent(SnakeGameEvent.PauseGame)
                                }
                            },
                            enabled = !state.isGameOver
                        ) {
                            Text(
                                text = when (state.gameState) {
                                    GameState.IDLE -> "Start"
                                    GameState.STARTED -> "Pause"
                                    GameState.PAUSED -> "Resume"
                                }
                            )
                        }
                    }
                }
            }

            // Game canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .aspectRatio(ratio = 2 / 3f)
                    .pointerInput(state.gameState) {
                        if (state.gameState != GameState.STARTED) {
                            return@pointerInput
                        }
                        detectTapGestures { offset ->
                            onEvent(SnakeGameEvent.UpdateDirection(offset, size.width))
                        }
                    }
            ) {
                val cellSize = size.width / 21
                drawGameBoard(
                    cellSize = cellSize,
                    cellColor = Custard,
                    borderCellColor = RoyalBlue,
                    gridWidth = state.xAxisGridSize,
                    gridHeight = state.yAxisGridSize
                )
                drawFood(
                    foodImage = foodImageBitmap,
                    cellSize = cellSize.toInt(),
                    coordinate = state.food
                )
                drawSnake(
                    snakeHeadImage = snakeHeadImageBitmap,
                    cellSize = cellSize,
                    snake = state.snake
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedVisibility(
            visible = state.isGameOver,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Game Over",
                    style = MaterialTheme.typography.displayMedium
                )
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Button(
                        onClick = { onEvent(SnakeGameEvent.ResetGame) },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Play Again")
                    }
                    Button(
                        onClick = { onEvent(SnakeGameEvent.ExitGame) }
                    ) {
                        Text("Exit")
                    }
                }
            }
        }
    }
}