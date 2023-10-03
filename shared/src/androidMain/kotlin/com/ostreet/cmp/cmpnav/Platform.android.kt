package com.ostreet.cmp.cmpnav

import androidx.compose.runtime.Composable
import com.ostreet.cmp.cmpnav.ui.App

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
fun MainView() = App()