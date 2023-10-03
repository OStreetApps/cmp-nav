package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

open class NavGraph(
    val graphRoute: String,
    startDestination: String,
    parentNavController: NavController? = null,
    vararg routers: NavDestRouter
) : NavRoute {

    var graphController: GraphController? = null

    init {
        var startDest: NavDestination? = null
        val nodes = mutableMapOf<String, () -> NavDestination>()
        val startList = mutableListOf<NavDestination>()
        routers.onEach {
            nodes[it.name] = it.routeBuilder
            if (it.name == startDestination) {
                val navDest = it.routeBuilder.invoke()
                startDest = navDest
                startList.add(navDest)
            }
        }
        if (startDest == null)
            throw IllegalArgumentException("Didn't find starting route in provided routes")
        startDest?.apply {
            graphController = GraphController(
                nodes = nodes,
                startList = startList,
                activeDestination = this,
                parentNavController = parentNavController
            )
        }
    }

    @Composable
    override fun Route(
        navController: NavController?,
        modifier: Modifier
    ) {
        graphController?.apply {
            val activeDest: NavDestination? by this.activeDestination.collectAsState(null)
            GraphPort(
                activeDest,
                this,
                modifier
            )
        }
    }

    @Composable
    fun GraphPort(
        activeDest: NavDestination?,
        navController: NavController?,
        modifier: Modifier
    ) {
        activeDest?.Route(
            navController,
            modifier
        )
    }
}