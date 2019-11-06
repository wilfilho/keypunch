package com.wilfilho.keypunch.dimensions

import android.graphics.Rect
import android.os.Build
import android.view.View
import com.wilfilho.keypunch.dimensions.DimensionBase

/**
 * Created by Wilson Martins on 2019-10-31.
 */
class KeyboardDimension(
    private val keyboardView: View,
    private val navigationBarHeight: Int,
    private val deviceHeight: Int
) : DimensionBase {
    override fun height(): Int {
        val keyboardRect = Rect()
        keyboardView.getWindowVisibleDisplayFrame(keyboardRect)
        val phisicalNavigationButton = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationBarHeight
        } else {
            0
        }

        return (deviceHeight - keyboardRect.bottom) - phisicalNavigationButton
    }
}