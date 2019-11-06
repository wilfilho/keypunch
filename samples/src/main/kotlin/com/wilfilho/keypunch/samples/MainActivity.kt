package com.wilfilho.keypunch.samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wilfilho.keypunch.Keyboard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyboard = Keyboard(this)
        keyboard.watch()

        keyboard.onKeyboardOpen {
            Log.d("KEYBOARD", it.toString())
        }

        keyboard.onKeyboardClose {
            Log.d("KEYBOARD", "CLOSED")
        }
    }
}
