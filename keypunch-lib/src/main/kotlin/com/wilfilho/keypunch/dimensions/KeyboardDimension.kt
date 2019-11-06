package com.wilfilho.keypunch.dimensions

import android.graphics.Rect
import android.os.Build
import android.view.View
import com.wilfilho.keypunch.dimensions.DimensionBase

/**
 * Created by Wilson Martins on 2019-10-31.
 */
internal class KeyboardDimension(
    private val keyboardView: View,
    private val navigationBarHeight: Int,
    private val deviceHeight: Int
) : DimensionBase {
    override fun height(): Int {
        val keyboardRect = Rect()
        keyboardView.getWindowVisibleDisplayFrame(keyboardRect)

        return (deviceHeight - keyboardRect.bottom) - navigationBarHeight
    }
}