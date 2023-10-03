package com.ostreet.cmp.cmpnav.ui.route.logged_out

import com.ostreet.cmp.cmpnav.ui.route.logged_out.login.loginScreenRoute
import com.ostreet.cmp.cmpnav.ui.route.logged_out.login.loginScreenRouter
import com.ostreet.cmp.cmpnav.ui.route.logged_out.sign_up.signUpScreenRouter
import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavGraph

const val loggedOutGraph = "loggedOutGraph"

class LoggedOutGraph(parentNavController: NavController) : NavGraph(
    graphRoute = loggedOutGraph,
    startDestination = loginScreenRoute,
    parentNavController = parentNavController,
    loginScreenRouter, signUpScreenRouter
)