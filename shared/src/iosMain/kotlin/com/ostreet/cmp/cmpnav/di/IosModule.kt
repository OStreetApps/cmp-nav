package com.ostreet.cmp.cmpnav.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(iosModule, sharedModule)
    }
}

val iosModule = module {
    //singleOf(::SettingsRepoImpl) { bind<SettingsRepo>() }

    //single { GoogleSignInClient() }
}