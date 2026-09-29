package com.example.myapplication_multy2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform