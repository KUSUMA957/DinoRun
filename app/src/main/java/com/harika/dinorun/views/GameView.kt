package com.harika.dinorun.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.harika.dinorun.R
import com.harika.dinorun.models.Cactus
import com.harika.dinorun.models.Cloud
import com.harika.dinorun.models.Dino
import com.harika.dinorun.models.Earth
import com.harika.dinorun.utils.CollisionDetector
import com.harika.dinorun.utils.Constants
import com.harika.dinorun.utils.ResourceManager

class GameView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread: GameThread? = null
    private var earth: Earth? = null
    private var dino: Dino? = null
    private var cacti: MutableList<Cactus>? = null
    private var isPaused = false
    private var isJumping = false
    private var scorePaint: Paint? = null
    private var highScorePaint: Paint? = null
    private var score = 0
    private var highScore = 0
    private var isGameOver = false
    private var gameOverPaint: Paint? = null
    private var replayPaint: Paint? = null
    private var clouds: MutableList<Cloud>? = null
    private val NUM_INITIAL_CLOUDS = 2
    private var replayX = 0f
    private var replayY = 0f

    init {
        createClouds()
        createEarth()
        initScore()
        gameOverStatus()
        holder.addCallback(this)
        isFocusable = true
    }

    private fun createClouds() {
        clouds = ArrayList()
        val y = randomYPosition
        for (i in 0 until NUM_INITIAL_CLOUDS) {
            val cloud = Cloud(context)
            val x = getNextCloudXPosition(i)
            cloud.setPosition(x, y)
            clouds?.add(cloud)
        }
    }

    private fun createEarth() {
        val earthX = 0f // Set the X position of the Earth
        val earthY = Constants.EARTH_Y_POSITION // Set the Y position of the Earth
        earth = Earth(earthX, earthY, resources.getColor(R.color.`object`))
    }

    private fun initScore() {
        score = 0
        highScore = 0
        scorePaint = Paint()
        scorePaint!!.color = resources.getColor(R.color.`object`)
        scorePaint!!.textSize = 48f
        scorePaint!!.setTypeface(Typeface.DEFAULT_BOLD)
        scorePaint!!.isAntiAlias = true
        highScorePaint = Paint()
        highScorePaint!!.color = resources.getColor(R.color.`object`)
        highScorePaint!!.textSize = 36f
        highScorePaint!!.setTypeface(Typeface.DEFAULT_BOLD)
        highScorePaint!!.isAntiAlias = true
    }

    private fun gameOverStatus() {
        isGameOver = false
        gameOverPaint = Paint()
        gameOverPaint!!.color = resources.getColor(R.color.`object1`)
        gameOverPaint!!.textSize = 64f
        gameOverPaint!!.setTypeface(Typeface.DEFAULT_BOLD)
        gameOverPaint!!.isAntiAlias = true
        replayPaint = Paint()
        replayPaint!!.color = resources.getColor(R.color.`object2`)
        replayPaint!!.textSize = 48f
        replayPaint!!.setTypeface(Typeface.DEFAULT_BOLD)
        replayPaint!!.isAntiAlias = true
    }

    private fun getNextCloudXPosition(index: Int): Float {
        return (index + 1) * Constants.CLOUD_SPACING
    }

    private val randomYPosition: Float
        private get() = (Math.random() * (Constants.MAX_Y_POSITION - Constants.MIN_Y_POSITION) + Constants.MIN_Y_POSITION).toFloat()

    override fun surfaceCreated(holder: SurfaceHolder) {
        ResourceManager.getInstance(context) // Initialize the resource manager
        dino = Dino(
            context,
            Constants.SCREEN_WIDTH / 4
        ) // Adjust the dino's initial X position as needed
        cacti = ArrayList()
        isPaused = false
        gameThread = GameThread(holder)
        gameThread!!.setRunning(true)
        gameThread!!.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Not implemented
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread!!.setRunning(false)
                gameThread!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isGameOver && event.action == MotionEvent.ACTION_DOWN) {
            if (event.x >= replayX && event.x <= replayX + replayPaint!!.measureText("Tap to Replay") && event.y >= replayY - replayPaint!!.textSize && event.y <= replayY) {
                restartGame()
                return true
            }
        } else if (!isGameOver && event.action == MotionEvent.ACTION_DOWN) {
            if (!isPaused) {
                if (!isJumping) {
                    dino!!.jump()
                    isJumping = true
                }
            }
        } else if (event.action == MotionEvent.ACTION_UP) {
            isJumping = false
        }
        return true
    }

    fun update() {
        if (!isPaused) {
            dino!!.update()

            // Update cacti and remove those that are offscreen or collided
            for (i in cacti!!.indices.reversed()) {
                val cactus = cacti!![i]
                cactus.update()
                if (cactus.x + cactus.width < 0) {
                    cacti!!.removeAt(i)
                } else if (CollisionDetector.isCollision(dino!!, cactus)) {
                    gameOver()
                }
            }

            // Add new cacti periodically
            if (cacti!!.isEmpty() || cacti!![cacti!!.size - 1].x < Constants.SCREEN_WIDTH - Constants.CACTUS_SPAWN_DISTANCE) {
                val randomHeight =
                    (Math.random() * (Constants.CACTUS_MAX_HEIGHT - Constants.CACTUS_MIN_HEIGHT + 1) + Constants.CACTUS_MIN_HEIGHT).toInt()
                cacti!!.add(Cactus(context, Constants.SCREEN_WIDTH, randomHeight))
            }
            updateScore()
        }
    }

    private fun updateScore() {
        score++ // Increment the score by 1
        if (score > highScore) {
            highScore = score // Update the high score
        }
    }

    private fun drawClouds(canvas: Canvas) {
        for (cloud in clouds!!) {
            if (!isPaused) cloud.update()
            cloud.draw(canvas)
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(resources.getColor(R.color.background))
            if (isPaused) {
                // Draw bounding boxes for debugging
                if (Constants.DEBUG_MODE) {
                    val debugPaint = Paint()
                    debugPaint.color = Color.RED
                    debugPaint.style = Paint.Style.STROKE
                    debugPaint.strokeWidth = 2f
                    val dinoRect = dino!!.boundingBox
                    canvas.drawRect(dinoRect, debugPaint)
                    for (cactus in cacti!!) {
                        val cactusRect = cactus.boundingBox
                        canvas.drawRect(cactusRect, debugPaint)
                    }
                }
            }
            drawClouds(canvas)
            dino!!.draw(canvas)
            for (cactus in cacti!!) {
                cactus.draw(canvas)
            }
            earth!!.draw(canvas)


            // Draw the score and high score on the canvas
            val scoreText = "Score: $score"
            val highScoreText = "High Score: $highScore"
            val scoreX = width - scorePaint!!.measureText(scoreText) - 100
            val scoreY = 100f
            canvas.drawText(scoreText, scoreX, scoreY, scorePaint!!)
            val highScoreX = width - highScorePaint!!.measureText(highScoreText) - 100
            val highScoreY = scoreY + highScorePaint!!.textSize + 16
            canvas.drawText(highScoreText, highScoreX, highScoreY, highScorePaint!!)
            if (isGameOver) {
                val gameOverText = "Game Over"
                val replayText = "Tap to Replay"
                val gameOverX = (width - gameOverPaint!!.measureText(gameOverText)) / 2
                val gameOverY = height / 2 - gameOverPaint!!.textSize - height / 4
                replayX = (width - replayPaint!!.measureText(replayText)) / 2
                replayY = height / 2 + replayPaint!!.textSize - height / 4
                canvas.drawText(gameOverText, gameOverX, gameOverY, gameOverPaint!!)
                canvas.drawText(replayText, replayX, replayY, replayPaint!!)
            }
        }
    }

    private fun restartGame() {
        isGameOver = false
        isPaused = false
        score = 0

        // Remove the collided cactus
        if (cacti != null && cacti!!.size > 0) {
            val iterator = cacti!!.iterator()
            while (iterator.hasNext()) {
                val cactus = iterator.next()
                if (CollisionDetector.isCollision(dino!!, cactus)) {
                    iterator.remove()
                }
            }
        }
    }

    fun pauseGame() {
        isPaused = true
    }

    fun resumeGame() {
        isGameOver = false
        isPaused = false
    }

    private fun gameOver() {
        isGameOver = true
        pauseGame()
    }

    private inner class GameThread(private val surfaceHolder: SurfaceHolder) : Thread() {
        private var isRunning = false
        fun setRunning(running: Boolean) {
            isRunning = running
        }

        override fun run() {
            while (isRunning) {
                var canvas: Canvas? = null
                try {
                    canvas = surfaceHolder.lockCanvas()
                    synchronized(surfaceHolder) {
                        update()
                        draw(canvas)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }

                // Sleep for a short duration to control the game speed
                try {
                    sleep(Constants.GAME_SPEED.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
