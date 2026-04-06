package com.kaanf.detectiveaistories

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}
