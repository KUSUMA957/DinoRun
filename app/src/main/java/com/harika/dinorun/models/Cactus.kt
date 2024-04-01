package com.harika.dinorun.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.harika.dinorun.R
import com.harika.dinorun.utils.Constants
import com.harika.dinorun.utils.ResourceManager.Companion.getInstance
//
//class Cactus(context: Context?, var x: Int, height: Int) {
//    private val cactusDrawable: Drawable
//    val boundingBox: Rect
//    private val y: Int
//    val width: Int
//    private val height: Int
//    private val velocityX: Int
//
//    init {
//        cactusDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.cactus)
//        y = CACTUS_DEFAULT_Y
//        width = cactusDrawable.intrinsicWidth
//        this.height = cactusDrawable.intrinsicHeight
//        boundingBox = Rect(x, y, x + width, y + height)
//        velocityX = Constants.CACTUS_SPEED
//    }
//
//    fun update() {
//        x -= Constants.CACTUS_SPEED // Adjust the cactus speed as needed
//        boundingBox.left = x
//        boundingBox.right = x + width
//    }
//
//    fun draw(canvas: Canvas?) {
//        cactusDrawable.setBounds(x, y, x + width, y + height)
//        cactusDrawable.draw(canvas!!)
//    }
//
//    fun getBoundingBox(): Rect {
//        return Rect(x + 20, y, x + width - 20, y + height)
//    }
//
//    companion object {
//        private const val CACTUS_DEFAULT_Y = Constants.SCREEN_HEIGHT - Constants.CACTUS_HEIGHT
//    }
//}
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
//class Cactus(context: Context?, var x: Int, height: Int) {
//    private val cactusDrawable: Drawable
//    val boundingBox: Rect
//    private val y: Int
//    val width: Int
//    private val height: Int
//    private val velocityX: Int
//
//    init {
//        cactusDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.cactus)
//        y = CACTUS_DEFAULT_Y
//        width = cactusDrawable.intrinsicWidth
//        this.height = cactusDrawable.intrinsicHeight
//        boundingBox = Rect(x, y, x + width, y + height)
//        velocityX = Constants.CACTUS_SPEED
//    }
//
//    fun update() {
//        x -= Constants.CACTUS_SPEED // Adjust the cactus speed as needed
//        boundingBox.left = x
//        boundingBox.right = x + width
//    }
//
//    fun draw(canvas: Canvas?) {
//        cactusDrawable.setBounds(x, y, x + width, y + height)
//        cactusDrawable.draw(canvas!!)
//    }
//
//    // Renamed function to avoid clash
//    fun getBoundingBox(): Rect {
//        return Rect(x + 20, y, x + width - 20, y + height)
//    }
//
//    companion object {
//        private const val CACTUS_DEFAULT_Y = Constants.SCREEN_HEIGHT - Constants.CACTUS_HEIGHT
//    }
//}
class Cactus(context: Context?, var x: Int, height: Int) {
    private val cactusDrawable: Drawable
    private val y: Int
    val width: Int
    private val height: Int
    private val velocityX: Int

    // Explicitly declared property for boundingBox
    var boundingBox: Rect
        get() = Rect(x + 20, y, x + width - 20, y + height)

    init {
        cactusDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.cactus)
        y = CACTUS_DEFAULT_Y
        width = cactusDrawable.intrinsicWidth
        this.height = cactusDrawable.intrinsicHeight
        boundingBox = Rect(x, y, x + width, y + height)
        velocityX = Constants.CACTUS_SPEED
    }

    fun update() {
        x -= Constants.CACTUS_SPEED // Adjust the cactus speed as needed
        boundingBox.left = x
        boundingBox.right = x + width
    }

    fun draw(canvas: Canvas?) {
        cactusDrawable.setBounds(x, y, x + width, y + height)
        cactusDrawable.draw(canvas!!)
    }

    companion object {
        private const val CACTUS_DEFAULT_Y = Constants.SCREEN_HEIGHT - Constants.CACTUS_HEIGHT
    }
}

