package com.gowtham.compose_ratingbar_multiplatform

object LogMessage {

    private const val logVisible = false

    internal fun v(msg: String) {
        if (logVisible) println("Compose-Ratingbar :$msg")
    }

    internal fun e(msg: String) {
        if (logVisible) println("Compose-Ratingbar: $msg")
    }

}