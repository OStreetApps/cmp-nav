package com.ostreet.cmp.koin.navigation

data class NavDestRouter(
    val name: String,
    val routeBuilder: () -> NavDestination
)