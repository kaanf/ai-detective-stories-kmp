package com.kaanf.home.presentation.pub

sealed interface PubAction {
    data class OnItemActionClick(val itemId: String) : PubAction
}
