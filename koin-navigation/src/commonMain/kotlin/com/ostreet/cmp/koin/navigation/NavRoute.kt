package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface NavRoute {
    @Composable
    fun Route(
        navController: NavController?,
        modifier: Modifier
    )
}