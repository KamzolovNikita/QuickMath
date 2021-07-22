package com.example.android.recklinetest.util

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator


object Animation {
    fun animatedButtonClick(view: View) {
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f)
        )
        scaleDown.duration = 500
        scaleDown.start()
    }

    fun animatedAnswerHighlighting(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
}