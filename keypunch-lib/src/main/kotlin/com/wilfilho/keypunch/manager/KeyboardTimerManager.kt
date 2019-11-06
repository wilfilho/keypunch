package com.wilfilho.keypunch.manager

import android.os.CountDownTimer
import com.wilfilho.keypunch.configs.KeyboardTimerConfig

/**
 * Created by Wilson Martins on 2019-10-25.
 */

internal class KeyboardTimerManager(
    private val dimensionsManager: KeyboardHeightProcessor
) : CountDownTimer(KeyboardTimerConfig.MILLIS, KeyboardTimerConfig.INTERVAL) {

    // last keyboard height
    private var lastKeyboardHeight = 0

    // keyboard session heights
    private var keyboardSessionHeights = arrayListOf<Int>()

    private var isKeyboardOpened: Boolean = false

    var keyboardHeightEvent: (altura: Int) -> Unit? = {}

    override fun onFinish() {
        keyboardSessionHeights.max()?.let {
            if (it > 0 && lastKeyboardHeight != it) {
                lastKeyboardHeight = -1
            }

            keyboardHeightEvent(it)
            isKeyboardOpened = false
            keyboardSessionHeights.clear()
            lastKeyboardHeight = it

            this.cancel()
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        val alturaTecladoCalculada = dimensionsManager.keyboardHeight()
        keyboardSessionHeights.add(alturaTecladoCalculada)
    }

    fun onKeyboardHeight(action: (height: Int) -> Unit) {
        keyboardHeightEvent = action
    }

    fun safeStart() {
        if (!isKeyboardOpened || keyboardSessionHeights.size == 0 && isKeyboardOpened) {
            isKeyboardOpened = true
            this.start()
        }
    }
}