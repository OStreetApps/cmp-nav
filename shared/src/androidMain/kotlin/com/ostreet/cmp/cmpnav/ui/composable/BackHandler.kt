package com.ostreet.cmp.cmpnav.ui.composable

import androidx.compose.runtime.Composable
import androidx.activity.compose.BackHandler as ActivityBackHandler

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    ActivityBackHandler(enabled = true, onBack = onBack)
}