package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow

class NavHostController(val parentNavController: NavController?) : NavController {

    private val graphMap = mutableMapOf<String, NavGraph>()
    val activeGraph = MutableStateFlow<NavGraph?>(null)

    fun registerGraphs(startGraph: String, vararg graphs: NavGraph) {
        graphs.onEach {
            graphMap[it.graphRoute] = it
            if (startGraph == it.graphRoute) {
                activeGraph.value = it
            }
        }
    }

    override fun navigate(route: String) {
        if (graphMap.containsKey(route)) {
            graphMap[route]?.apply {
                activeGraph.value = this
            }
        } else {
            parentNavController?.navigate(route)
        }
    }

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }
}

@Composable
fun NavHostController.currentGraphAsState(): State<NavGraph?> =
    this.activeGraph.collectAsState(null)