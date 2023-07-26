package com.gowtham.compose_ratingbar_multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform