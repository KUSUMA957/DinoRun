package com.harika.dinorun.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.harika.dinorun.R
import com.harika.dinorun.utils.Constants
import com.harika.dinorun.utils.ResourceManager.Companion.getInstance

class Cloud(context: Context?) {
    private val cloudDrawable: Drawable
    private var x = 0f
    private var y = 0f
    private val width: Int
    private val height: Int
    private val velocityX = Constants.CLOUD_VELOCITY

    init {
        cloudDrawable = getInstance(context!!)!!.getDrawableFromResource(R.drawable.cloud)
        width = cloudDrawable.intrinsicWidth
        height = cloudDrawable.intrinsicHeight
    }

    fun update() {
        x += velocityX
        if (x < -cloudDrawable.intrinsicWidth) {
            x = Constants.SCREEN_WIDTH.toFloat()
        }
    }

    fun draw(canvas: Canvas?) {
        cloudDrawable.setBounds(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
        cloudDrawable.draw(canvas!!)
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}
