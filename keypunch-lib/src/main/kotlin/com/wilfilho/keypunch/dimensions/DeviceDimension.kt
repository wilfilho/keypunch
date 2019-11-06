package com.wilfilho.keypunch.dimensions

import android.app.Activity
import android.graphics.Point
import android.view.Display

/**
 * Created by Wilson Martins on 2019-10-31.
 */
class DeviceDimension(private val activity: Activity) : DimensionBase {
    override fun height(): Int {
        val pointManager = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java).invoke(
                activity.windowManager.defaultDisplay, pointManager)
        return pointManager.y
    }
}