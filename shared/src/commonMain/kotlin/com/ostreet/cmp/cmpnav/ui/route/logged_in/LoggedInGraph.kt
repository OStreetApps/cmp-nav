package com.ostreet.cmp.cmpnav.ui.route.logged_in

import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavGraph

const val loggedInGraph = "loggedInGraph"

class LoggedInGraph(parentNavController: NavController) : NavGraph(
    graphRoute = loggedInGraph,
    startDestination = loggedInRoute,
    parentNavController = parentNavController,
    loggedInRouter
)