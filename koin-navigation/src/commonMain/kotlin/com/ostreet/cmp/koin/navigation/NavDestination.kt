package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class NavDestination(val route: String) : ScopedComponent(), NavRoute {

    override val scope: Scope by lazy { createScope(this) }

    @Composable
    abstract override fun Route(
        navController: NavController?,
        modifier: Modifier
    )
}