package com.ostreet.cmp.cmpnav.ui.route.logged_in.friends

import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavGraph

const val friendsGraph = "friendsGraph"

class FriendsGraph(parentNavController: NavController) : NavGraph(
    graphRoute = friendsGraph,
    startDestination = friendsScreenRoute,
    parentNavController = parentNavController,
    friendsScreenRouter, friendsNestedRouter
)