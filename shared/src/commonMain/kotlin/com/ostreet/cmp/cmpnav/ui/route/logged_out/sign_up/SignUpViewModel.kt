package com.ostreet.cmp.cmpnav.ui.route.logged_out.sign_up

import com.ostreet.cmp.cmpnav.repo.SettingsRepo
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    private val mutableLocalFlow = MutableStateFlow(false)
    val uiState: StateFlow<SignUpUiState> = mutableLocalFlow
        .combine(settingsRepo.remoteBoolean) {
            localValue, remoteValue ->
            SignUpUiState(
                localBoolean = localValue,
                remoteBoolean = remoteValue
            )
        }.stateIn(scope = viewModelScope,
            initialValue = SignUpUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun toggleLocalBoolean() = viewModelScope.launch {
        mutableLocalFlow.value = !mutableLocalFlow.value
    }

    fun toggleRemoteBoolean() = viewModelScope.launch {
        settingsRepo.toggleRemoteBoolean()
    }
}


data class SignUpUiState(
    val localBoolean: Boolean = false,
    val remoteBoolean: Boolean = false
)