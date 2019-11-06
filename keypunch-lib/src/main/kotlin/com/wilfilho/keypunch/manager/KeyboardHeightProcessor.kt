package com.wilfilho.keypunch.manager

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import com.wilfilho.keypunch.dimensions.NotchDimension

/**
 * Created by Wilson Martins on 2019-10-25.
 */

internal interface KeyboardHeightProcessor {
    fun keyboardHeight(): Int

    fun getRealDeviceHeight(): Int

    fun getNavigationBarHeight(): Int

    fun getNotchHeight(): Int
}

internal class KeyboardHeightProcessorImpl(
    private val activity: Activity,
    private val keyboardView: View
) : KeyboardHeightProcessor {
    override fun getNotchHeight(): Int {
        val notchHeight = NotchDimension(activity)
        return notchHeight.height()
    }

    override fun keyboardHeight(): Int {
        val keyboardRect = Rect()
        keyboardView.getWindowVisibleDisplayFrame(keyboardRect)
        val realHeight = getRealDeviceHeight()
        val phisicalNavigationButton = getNavigationBarHeight()

        return ((realHeight - keyboardRect.bottom) - phisicalNavigationButton) + getNotchHeight()
    }

    override fun getRealDeviceHeight(): Int {
        val dimensoesReais = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java).invoke(
                activity.windowManager.defaultDisplay, dimensoesReais)
        return dimensoesReais.y
    }

    override fun getNavigationBarHeight(): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val usableHeight = metrics.heightPixels
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)

        return metrics.heightPixels - usableHeight
    }
}