package com.ostreet.cmp.koin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow

class GraphController(
    activeDestination: NavDestination,
    val startList: List<NavDestination>,
    val nodes: Map<String, () -> NavDestination>,
    val parentNavController: NavController? = null
) : NavController {


    private val stack : ArrayDeque<NavDestination> = ArrayDeque(startList)
    val activeDestination = MutableStateFlow(activeDestination)
    override fun navigate(route: String) {
        if (nodes.containsKey(route)) {
            nodes[route]?.apply {
                val navDest = this.invoke()
                stack.addLast(navDest)
                activeDestination.value = navDest
            }
        } else {
            parentNavController?.navigate(route)
        }
    }

    override fun onBackPressed() {
        if (stack.count() > 1) {
            stack.removeLast()
            activeDestination.value = stack.last()
        }
    }
}

@Composable
fun GraphController.currentDestAsState(): State<NavDestination?> =
    this.activeDestination.collectAsState(null)
