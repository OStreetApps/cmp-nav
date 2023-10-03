package com.ostreet.cmp.cmpnav

import androidx.compose.ui.window.ComposeUIViewController
import com.ostreet.cmp.cmpnav.ui.App
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

fun MainViewController() = ComposeUIViewController {
    App()
}