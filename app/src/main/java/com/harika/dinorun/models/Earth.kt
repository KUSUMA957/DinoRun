package com.harika.dinorun.models

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.PathEffect
import com.harika.dinorun.utils.Constants.EARTH_GROUND_STROKE_WIDTH

class Earth(xPosition: Float, private val yPosition: Float, color: Int) {
    private val solidLinePaint: Paint
    private val dottedLinePaint: Paint
    private val dottedLineEffect: PathEffect

    init {
        solidLinePaint = Paint()
        solidLinePaint.color = color
        solidLinePaint.strokeWidth = EARTH_GROUND_STROKE_WIDTH
        dottedLinePaint = Paint()
        dottedLinePaint.color = color
        dottedLinePaint.strokeWidth = EARTH_GROUND_STROKE_WIDTH
        dottedLineEffect = DashPathEffect(floatArrayOf(20f, 40f), 0f)
        dottedLinePaint.setPathEffect(dottedLineEffect)
    }

    fun update() {
        // Update any necessary logic for the Earth object
    }

    fun draw(canvas: Canvas) {
        val solidLineY = yPosition
        val dottedLineY = yPosition + 15f
        canvas.drawLine(0f, solidLineY, canvas.width.toFloat(), solidLineY, solidLinePaint)
        canvas.drawLine(0f, dottedLineY, canvas.width.toFloat(), dottedLineY, dottedLinePaint)
    }
}
