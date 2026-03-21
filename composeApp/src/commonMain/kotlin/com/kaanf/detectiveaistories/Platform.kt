package com.kaanf.detectiveaistories

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
