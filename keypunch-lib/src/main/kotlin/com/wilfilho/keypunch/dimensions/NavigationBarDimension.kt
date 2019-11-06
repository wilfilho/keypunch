package com.wilfilho.keypunch.dimensions

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.wilfilho.keypunch.dimensions.DimensionBase

/**
 * Created by Wilson Martins on 2019-10-31.
 */
class NavigationBarDimension(private val activity: Activity) : DimensionBase {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun height(): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)

        return metrics.heightPixels - usableHeight
    }
}