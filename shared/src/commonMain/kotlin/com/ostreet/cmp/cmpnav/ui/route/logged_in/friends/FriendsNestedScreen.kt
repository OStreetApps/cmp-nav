package com.ostreet.cmp.cmpnav.ui.route.logged_in.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ostreet.cmp.cmpnav.ui.composable.BasicButton
import com.ostreet.cmp.cmpnav.ui.extension.basicButton
import com.ostreet.cmp.koin.navigation.NavController
import com.ostreet.cmp.koin.navigation.NavDestRouter
import com.ostreet.cmp.koin.navigation.NavDestination

const val friendsNestedRoute = "friendsNestedRoute"

val friendsNestedRouter = NavDestRouter(
    name = friendsNestedRoute,
    routeBuilder = { FriendsNested() }
)

class FriendsNested : NavDestination(friendsNestedRoute) {

    private val viewModel: FriendsNestedViewModel = scope.get()

    @Composable
    override fun Route(
        navController: NavController?,
        modifier: Modifier
        ) {
            val uiState by viewModel.uiState.collectAsState()
            FriendsNestedScreen(
                uiState = uiState,
                modifier = modifier,
                toggleLocalBoolean = viewModel::toggleLocalBoolean,
                toggleRemoteBoolean = viewModel::toggleRemoteBoolean,
                navigateBack = { navController?.onBackPressed() }
            )
        }

    @Composable
    internal fun FriendsNestedScreen(
        uiState: FriendsNestedUiState,
        modifier: Modifier,
        toggleLocalBoolean: () -> Unit = {},
        toggleRemoteBoolean: () -> Unit = {},
        navigateBack: () -> Unit = {}
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier =
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Friends Nested Screen")

                BasicButton(
                    "Back to Friends",
                    Modifier.basicButton()
                ) { navigateBack() }

                Text("Screen Scoped Variable is ${uiState.localBoolean}")

                BasicButton(
                    "Toggle Local Value",
                    Modifier.basicButton(),
                ) { toggleLocalBoolean() }

                Text("Singleton Scoped Variable is ${uiState.remoteBoolean}")

                BasicButton(
                    "Toggle Remote Value",
                    Modifier.basicButton(),
                ) { toggleRemoteBoolean() }
            }
        }
    }
}
