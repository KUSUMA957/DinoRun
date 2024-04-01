package com.harika.dinorun.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray

class ResourceManager private constructor(context: Context) {
    private val context: Context
    private val bitmapCache: SparseArray<Bitmap>

    init {
        this.context = context.applicationContext
        bitmapCache = SparseArray()
    }

    fun getDrawableFromResource(resourceId: Int): Drawable {
        return context.resources.getDrawable(resourceId)
    }

    companion object {
        private var instance: ResourceManager? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): ResourceManager? {
            if (instance == null) {
                instance = ResourceManager(context)
            }
            return instance
        }
    }
}
