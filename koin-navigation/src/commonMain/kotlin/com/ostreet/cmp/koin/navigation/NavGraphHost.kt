package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavGraphHost(
    activeGraph: NavGraph?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    activeGraph?.Route(
        navController,
        modifier
    )
}