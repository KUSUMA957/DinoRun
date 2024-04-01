package com.harika.dinorun

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.harika.dinorun.views.GameView

class MainActivity : Activity() {
    private var gameView: GameView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the title bar and set full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Create and set the game view
        gameView = GameView(this)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resumeGame()
    }

    override fun onPause() {
        super.onPause()
        gameView!!.pauseGame()
    }
}
