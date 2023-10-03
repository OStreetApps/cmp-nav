package com.ostreet.cmp.cmpnav.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsRepo {
    val remoteBoolean: Flow<Boolean>
    val loggedIn: Flow<Boolean>

    suspend fun toggleRemoteBoolean()
    suspend fun login()
    suspend fun logout()

}

class SettingsRepoImpl: SettingsRepo {

    private val mutableRemoteBoolean = MutableStateFlow(false)
    override val remoteBoolean: Flow<Boolean> = mutableRemoteBoolean

    private val mutableLoggedIn = MutableStateFlow(false)
    override val loggedIn: Flow<Boolean> = mutableLoggedIn

    override suspend fun toggleRemoteBoolean() {
        mutableRemoteBoolean.value = !mutableRemoteBoolean.value
    }

    override suspend fun login() {
        mutableLoggedIn.value = true
    }

    override suspend fun logout() {
        mutableLoggedIn.value = false
    }


}