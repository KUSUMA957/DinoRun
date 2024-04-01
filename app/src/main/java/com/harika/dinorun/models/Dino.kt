//package com.harika.dinorun.models
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Rect
//import android.graphics.drawable.Drawable
//import com.harika.dinorun.R
//import com.harika.dinorun.utils.Constants
//import com.harika.dinorun.utils.ResourceManager.Companion.getInstance
//
//class Dino(context: Context?, private val x: Int) {
//    private val dinoDrawable: Drawable
//    val boundingBox: Rect
//    private var y: Int
//    private val width: Int
//    private val height: Int
//    private var velocityY = 0
//    private var isJumping = false
//
//    init {
//        dinoDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.dino)
//        y = DEFAULT_DINO_Y
//        width = dinoDrawable.intrinsicWidth
//        height = dinoDrawable.intrinsicHeight
//        boundingBox = Rect(x, y, x + width, y + height)
//    }
//
//    fun update() {
//        velocityY += GRAVITY
//        y += velocityY
//        if (y > DEFAULT_DINO_Y) {
//            y = DEFAULT_DINO_Y
//            velocityY = 0
//            isJumping = false
//        }
//        boundingBox.left = x
//        boundingBox.right = x + width
//        boundingBox.top = y
//        boundingBox.bottom = y + height
//    }
//
//    fun draw(canvas: Canvas?) {
//        dinoDrawable.setBounds(x, y, x + width, y + height)
//        dinoDrawable.draw(canvas!!)
//    }
//
//    fun jump() {
//        if (!isJumping) {
//            velocityY = JUMP_VELOCITY
//            isJumping = true
//        }
//    }
//
//    fun getBoundingBox(): Rect {
//        return Rect(x + 20, y + 10, x + width - 20, y + height - 10)
//    }
//
//    companion object {
//        private const val DEFAULT_DINO_Y =
//            Constants.SCREEN_HEIGHT - Constants.DINO_HEIGHT // Update the default Y position
//        private const val GRAVITY = 2
//        private const val JUMP_VELOCITY = -35
//    }
//}
package com.harika.dinorun.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.harika.dinorun.R
import com.harika.dinorun.utils.Constants
import com.harika.dinorun.utils.ResourceManager.Companion.getInstance

class Dino(context: Context?, private val x: Int) {
    private val dinoDrawable: Drawable
    private var y: Int
    private val width: Int
    private val height: Int
    private var velocityY = 0
    private var isJumping = false

    // Explicitly declared property for boundingBox
    var boundingBox: Rect
        get() = Rect(x + 20, y + 10, x + width - 20, y + height - 10)

    init {
        dinoDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.dino)
        y = DEFAULT_DINO_Y
        width = dinoDrawable.intrinsicWidth
        height = dinoDrawable.intrinsicHeight
        boundingBox = Rect(x, y, x + width, y + height)
    }

    fun update() {
        velocityY += GRAVITY
        y += velocityY
        if (y > DEFAULT_DINO_Y) {
            y = DEFAULT_DINO_Y
            velocityY = 0
            isJumping = false
        }
        boundingBox.left = x
        boundingBox.right = x + width
        boundingBox.top = y
        boundingBox.bottom = y + height
    }

    fun draw(canvas: Canvas?) {
        dinoDrawable.setBounds(x, y, x + width, y + height)
        dinoDrawable.draw(canvas!!)
    }

    fun jump() {
        if (!isJumping) {
            velocityY = JUMP_VELOCITY
            isJumping = true
        }
    }

    companion object {
        private const val DEFAULT_DINO_Y =
            Constants.SCREEN_HEIGHT - Constants.DINO_HEIGHT // Update the default Y position
        private const val GRAVITY = 2
        private const val JUMP_VELOCITY = -35
    }
}

