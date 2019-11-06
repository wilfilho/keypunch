package com.wilfilho.keypunch

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.wilfilho.keypunch.manager.KeyboardHeightProcessor
import com.wilfilho.keypunch.manager.KeyboardHeightProcessorImpl
import com.wilfilho.keypunch.manager.KeyboardTimerManager

/**
 * Created by Wilson Martins on 2019-10-25.
 */

class Keyboard(private val activity: Activity) : PopupWindow(activity) {

    // keyboard action listeners -> this objects will call when Keyboard handle keyboard
    // open event or close event;
    private var openKeyboardEvent: (altura: Int) -> Unit? = {}
    private var closeKeyboardEvent: () -> Unit? = {}

    // screen parent view
    private var parentView: View = activity.findViewById(android.R.id.content)

    // keyboard popup view
    private lateinit var keyboardView: View

    private lateinit var keyboardTimerManager: KeyboardTimerManager

    private var started: Boolean = false

    init {
        inflateView()
        setuKeyboardPopupConfigs()
        setuptKeyboardProcessor()

        // captures any change on layout
        registerLayoutChangesListener()
    }

    private fun inflateView() {
        val inflator = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        keyboardView = inflator.inflate(R.layout.keyboard_popup, null, false)        // define this content view as keyboard view
        contentView = keyboardView
    }

    private fun setuptKeyboardProcessor() {
        val keyboardProcessor: KeyboardHeightProcessor = KeyboardHeightProcessorImpl(
            activity, keyboardView)

        // setup keyboard notify events
        keyboardTimerManager = KeyboardTimerManager(keyboardProcessor)
        keyboardTimerManager.onKeyboardHeight {
            if (started) {
                if (it == 0) {
                    closeKeyboardEvent()
                } else {
                    openKeyboardEvent(it)
                }
            } else {
                started = true
            }
        }
    }

    private fun setuKeyboardPopupConfigs() {
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED

        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT
    }

    private fun registerLayoutChangesListener() {
        keyboardView.viewTreeObserver?.addOnGlobalLayoutListener {
            keyboardTimerManager.safeStart()
        }
    }

    fun watch() {
        Handler().postDelayed({
            if (!isShowing && parentView.windowToken != null) {
                setBackgroundDrawable(ColorDrawable(0))
                showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0)
            }
        }, 200)
    }

    fun onKeyboardOpen(action: (height: Int) -> Unit) {
        openKeyboardEvent = action
    }

    fun onKeyboardClose(action: () -> Unit) {
        closeKeyboardEvent = action
    }
}