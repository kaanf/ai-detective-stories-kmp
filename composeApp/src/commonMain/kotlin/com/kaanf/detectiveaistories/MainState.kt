package com.kaanf.detectiveaistories

data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = true,
    val isCharacterCreated: Boolean = false
)
