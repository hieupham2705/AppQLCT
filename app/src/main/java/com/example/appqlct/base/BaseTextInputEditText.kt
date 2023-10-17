package com.example.appqlct.base

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.example.appqlct.extension.hideKeyboard
import com.google.android.material.textfield.TextInputEditText

class BaseTextInputEditText(context: Context, attrs: AttributeSet?) : TextInputEditText(context, attrs){
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) this.hideKeyboard()
    }
}