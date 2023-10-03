package com.ostreet.cmp.cmpnav.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ostreet.cmp.cmpnav.ui.composable.BackHandler
import com.ostreet.cmp.cmpnav.ui.route.logged_in.LoggedInGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.loggedInGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_out.LoggedOutGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_out.loggedOutGraph
import com.ostreet.cmp.koin.navigation.NavGraph
import com.ostreet.cmp.koin.navigation.NavGraphHost
import com.ostreet.cmp.koin.navigation.NavHostController
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun App(viewModel: AppViewModel = getKoin().get()) {

    val uiState: Boolean by viewModel.uiState.collectAsState()

    MaterialTheme {
        val startDest = if (uiState) {
            loggedInGraph
        } else {
            loggedOutGraph
        }

        val navController = remember {
            NavHostController(null)
        }

        val navGraphs = remember {
            arrayOf(
                LoggedOutGraph(parentNavController = navController),
                LoggedInGraph(parentNavController = navController)
            )
        }

        navController.registerGraphs(startDest, *navGraphs)

        val activeGraph: NavGraph? by navController
            .activeGraph.collectAsState(null)
        BackHandler {
            activeGraph?.graphController?.onBackPressed()
        }
        NavGraphHost(
            activeGraph = activeGraph,
            navController = navController,
            modifier = Modifier
        )
    }
}
