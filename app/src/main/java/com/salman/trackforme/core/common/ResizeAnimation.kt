package com.salman.trackforme.core.common

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager

fun View.slideVisibility(visibility: Boolean, duration: Long = 500) {
    val transition: Transition = Slide(Gravity.BOTTOM)
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.isVisible = visibility
}