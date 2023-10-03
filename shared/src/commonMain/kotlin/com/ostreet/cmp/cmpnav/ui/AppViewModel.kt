package com.ostreet.cmp.cmpnav.ui

import com.ostreet.cmp.cmpnav.repo.SettingsRepo
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    settingsRepo: SettingsRepo
): ViewModel() {
    val uiState = settingsRepo.loggedIn.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}