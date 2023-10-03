package com.ostreet.cmp.cmpnav.ui.route.logged_out.login

import com.ostreet.cmp.cmpnav.repo.SettingsRepo
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val settingsRepo: SettingsRepo,
) : ViewModel() {

    private val mutableLocalFlow = MutableStateFlow(false)
    val uiState: StateFlow<LoginUiState> = mutableLocalFlow
        .combine(settingsRepo.remoteBoolean) {
            localValue, remoteValue ->
            LoginUiState(
                localBoolean = localValue,
                remoteBoolean = remoteValue
            )
        }.stateIn(scope = viewModelScope,
            initialValue = LoginUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun onSignInClick(navigateToHome: () -> Unit) = viewModelScope.launch {
        settingsRepo.login()
        navigateToHome()
    }

    fun toggleLocalBoolean() = viewModelScope.launch {
        mutableLocalFlow.value = !mutableLocalFlow.value
    }

    fun toggleRemoteBoolean() = viewModelScope.launch {
        settingsRepo.toggleRemoteBoolean()
    }
}

data class LoginUiState(
    val localBoolean: Boolean = false,
    val remoteBoolean: Boolean = false
)