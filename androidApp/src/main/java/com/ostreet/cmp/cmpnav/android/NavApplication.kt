package com.ostreet.cmp.cmpnav.android

import android.app.Application
import com.ostreet.cmp.cmpnav.android.di.androidModule
import com.ostreet.cmp.cmpnav.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class NavApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NavApplication)
            modules(androidModule, sharedModule)
        }
    }
}
