package com.ostreet.cmp.cmpnav.ui.route.logged_in.friends

import com.ostreet.cmp.cmpnav.repo.SettingsRepo
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FriendsNestedViewModel(
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    private val mutableLocalFlow = MutableStateFlow(false)
    val uiState: StateFlow<FriendsNestedUiState> = mutableLocalFlow
        .combine(settingsRepo.remoteBoolean) {
            localValue, remoteValue ->
            FriendsNestedUiState(
                localBoolean = localValue,
                remoteBoolean = remoteValue
            )
        }.stateIn(scope = viewModelScope,
            initialValue = FriendsNestedUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun toggleLocalBoolean() = viewModelScope.launch {
        mutableLocalFlow.value = !mutableLocalFlow.value
    }

    fun toggleRemoteBoolean() = viewModelScope.launch {
        settingsRepo.toggleRemoteBoolean()
    }
}


data class FriendsNestedUiState(
    val localBoolean: Boolean = false,
    val remoteBoolean: Boolean = false
)