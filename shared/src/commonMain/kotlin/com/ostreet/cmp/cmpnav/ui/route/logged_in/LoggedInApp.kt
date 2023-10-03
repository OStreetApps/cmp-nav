package com.ostreet.cmp.cmpnav.ui.route.logged_in

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton as M3IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ostreet.cmp.cmpnav.ui.composable.BackHandler
import com.ostreet.cmp.cmpnav.ui.composable.BottomNavBar
import com.ostreet.cmp.cmpnav.ui.designsystem.IconButtonItem
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.FriendsGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.HomeGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.homeGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.SettingsGraph
import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavDestRouter
import com.ostreet.cmp.koin.navigation.NavDestination
import com.ostreet.cmp.koin.navigation.NavGraph
import com.ostreet.cmp.koin.navigation.NavGraphHost
import com.ostreet.cmp.koin.navigation.NavHostController

const val loggedInRoute = "loggedInRoute"

val loggedInRouter = NavDestRouter(
    name = loggedInRoute,
    routeBuilder = { LoggedInApp() }
)

class LoggedInApp : NavDestination(loggedInRoute) {

    @Composable
    override fun Route(
        navController: NavController?,
        modifier: Modifier
    ) {
        Screen(
            startDest = homeGraph,
            parentNavController = navController
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        startDest: String,
        parentNavController: NavController?,
        modifier: Modifier = Modifier
    ) {

        val appState = rememberAppState(
            NavHostController(parentNavController)
        )

        val navGraphs = remember {
            arrayOf(
                HomeGraph(appState.navHostController),
                FriendsGraph(appState.navHostController),
                SettingsGraph(appState.navHostController)
            )
        }

        appState.navHostController.registerGraphs(startDest, *navGraphs)

        val snackbarHostState = remember { SnackbarHostState() }

        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                modifier = Modifier,
                snackbarHost = {
                    SnackbarHost(
                        snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = {snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                },
                topBar = {
                    TopAppBar(
                        title = { Text(appState.currentGraphTitle) },
                        navigationIcon = { appState.currentBackIcon.IconButton() },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        bottomNavIcons = appState.bottomNavIcons,
                        onNavigateToGraph = {
                            appState.navigateToGraph(it)
                        },
                        currentGraph = appState.currentGraph?.graphRoute ?: "",
                        bottomNavBadgeState = emptyMap(),//bottomNavBadgeState,
                        modifier = Modifier.testTag("BottomNavBar")

                    )
                },
            ) {
                val activeGraph: NavGraph? by appState.navHostController.activeGraph.collectAsState(null)
                BackHandler {
                    activeGraph?.graphController?.onBackPressed()
                }
                NavGraphHost(
                    activeGraph = activeGraph,
                    navController = appState.navHostController,
                    modifier = modifier
                )
            }
        }
    }

    @Composable
    fun IconButtonItem.IconButton() {
        when (this) {
            is IconButtonItem.NoIcon -> {}
            is IconButtonItem.BitmapIcon -> M3IconButton(onClick = onClick) {
                Icon(
                    bitmap = imageBitmap,
                    contentDescription = contentDes,
                    tint = Color.Unspecified
                )
            }
            is IconButtonItem.VectorIcon -> M3IconButton(onClick = onClick) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDes,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun rememberAppState(navHostController: NavHostController) =
    remember(navHostController) {
        LoggedInAppState(navHostController)
    }
