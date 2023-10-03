package com.ostreet.cmp.cmpnav.ui.route.logged_in.settings

import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavGraph

const val settingsGraph = "settingsGraph"

class SettingsGraph(parentNavController: NavController) : NavGraph(
    graphRoute = settingsGraph,
    startDestination = settingsScreenRoute,
    parentNavController = parentNavController,
    settingsScreenRouter
)