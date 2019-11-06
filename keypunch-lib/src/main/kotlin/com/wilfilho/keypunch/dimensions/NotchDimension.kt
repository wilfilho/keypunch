package com.wilfilho.keypunch.dimensions

import android.app.Activity

internal class NotchDimension(
    private val activity: Activity
) : DimensionBase {
    override fun height(): Int {
        var statusBar = 0
        val resources = activity.resources
        val resourceId = resources.getIdentifier(
            "status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val previewHeight = resources.getDimensionPixelSize(resourceId)
            if (previewHeight > 100) {
                statusBar = previewHeight
            }
        }
        return statusBar
    }
}