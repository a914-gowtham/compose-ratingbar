package com.gowtham.ratingbar

import android.util.Log

object LogMessage {

    private const val logVisible = false

    internal fun v(msg: String) {
        if (logVisible) Log.v("Compose-Ratingbar :", msg)
    }

    internal fun e(msg: String) {
        if (logVisible) Log.e("Compose-Ratingbar", msg)
    }

}