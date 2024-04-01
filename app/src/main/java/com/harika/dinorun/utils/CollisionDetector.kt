package com.harika.dinorun.utils

import android.graphics.Rect
import com.harika.dinorun.models.Cactus
import com.harika.dinorun.models.Dino

object CollisionDetector {
    fun isCollision(dino: Dino, cactus: Cactus): Boolean {
        val dinoRect = dino.boundingBox
        val cactusRect = cactus.boundingBox
        return Rect.intersects(dinoRect, cactusRect)
    }
}
