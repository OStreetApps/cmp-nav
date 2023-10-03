package com.ostreet.cmp.cmpnav.ui.route.logged_in.home

import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavGraph

const val homeGraph = "homeGraph"

class HomeGraph(parentNavController: NavController) : NavGraph(
    graphRoute = homeGraph,
    startDestination = homeScreenRoute,
    parentNavController = parentNavController,
    homeScreenRouter, homeNestedRouter
)