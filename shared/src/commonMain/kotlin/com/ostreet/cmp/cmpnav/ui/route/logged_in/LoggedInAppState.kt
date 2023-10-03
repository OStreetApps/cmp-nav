package com.ostreet.cmp.cmpnav.ui.route.logged_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.ostreet.cmp.cmpnav.ui.composable.BottomNavIcon
import com.ostreet.cmp.cmpnav.ui.designsystem.IconButtonItem
import com.ostreet.cmp.cmpnav.ui.designsystem.SupportedIcons
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.friendsGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.friendsScreenRoute
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.homeGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.homeScreenRoute
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.settingsGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.settingsScreenRoute
import com.ostreet.cmp.koin.navigation.GraphController
import com.ostreet.cmp.koin.navigation.NavDestination
import com.ostreet.cmp.koin.navigation.NavGraph
import com.ostreet.cmp.koin.navigation.NavHostController
import com.ostreet.cmp.koin.navigation.currentDestAsState
import com.ostreet.cmp.koin.navigation.currentGraphAsState

@Stable
class LoggedInAppState(val navHostController: NavHostController) {

    val currentGraph: NavGraph?
        @Composable get() = navHostController.currentGraphAsState().value

    private val currentDest: NavDestination?
        @Composable get() = currentGraph?.graphController?.currentDestAsState()?.value

    val currentGraphTitle: String
        @Composable get() = when (currentGraph?.graphRoute) {
            homeGraph -> "Home"
            friendsGraph -> "Friends"
            settingsGraph -> "Settings"
            else -> ""
        }

    val currentTopLevelDestination: BottomNavIcon?
        @Composable get() = when (currentGraph?.graphRoute) {
            homeGraph -> BottomNavIcon.HOME
            friendsGraph -> BottomNavIcon.FRIENDS
            settingsGraph -> BottomNavIcon.SETTINGS
            else -> null
        }

    val currentBackIcon: IconButtonItem
        @Composable get() = when (currentDest?.route) {
            homeScreenRoute,
            friendsScreenRoute,
            settingsScreenRoute -> IconButtonItem.NoIcon
            else -> IconButtonItem.VectorIcon(
                imageVector = SupportedIcons.back,
                contentDes = "back",
                onClick = {
                    navHostController.activeGraph.value?.graphController?.onBackPressed()
                }
            )
        }

    val currentGraphController: GraphController?
        @Composable get() = currentGraph?.graphController

    val bottomNavIcons: List<BottomNavIcon> = BottomNavIcon.values().asList()

    fun navigateToGraph(bottomNavIcon: BottomNavIcon) {

        when (bottomNavIcon) {
            BottomNavIcon.HOME -> navHostController.navigate(homeGraph)
            BottomNavIcon.FRIENDS -> navHostController.navigate(friendsGraph)
            BottomNavIcon.SETTINGS -> navHostController.navigate(settingsGraph)
        }
    }
}
