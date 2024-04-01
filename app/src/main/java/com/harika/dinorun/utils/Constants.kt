package com.harika.dinorun.utils

import android.graphics.Color

object Constants {
    const val SCREEN_WIDTH = 720 // Replace with your desired screen width
    const val SCREEN_HEIGHT = 1280 // Replace with your desired screen height
    const val CACTUS_MIN_HEIGHT = SCREEN_HEIGHT / 4 // Minimum height of a spawned cactus
    const val CACTUS_MAX_HEIGHT = SCREEN_HEIGHT / 2 // Maximum height of a spawned cactus
    const val CACTUS_SPEED = 10
    const val BACKGROUND_COLOR = Color.WHITE
    const val FPS = 60
    const val GAME_SPEED = 1000 / FPS
    const val DINO_HEIGHT = 100
    const val CACTUS_HEIGHT = 100
    const val CACTUS_SPAWN_DISTANCE = 500
    const val DEBUG_MODE = true
    const val EARTH_GROUND_STROKE_WIDTH = 3f
    const val EARTH_Y_POSITION = SCREEN_HEIGHT.toFloat()
    const val MIN_Y_POSITION = 200f
    const val MAX_Y_POSITION = 400f
    const val CLOUD_SPACING = 200f
    const val CLOUD_VELOCITY = -2f
}
