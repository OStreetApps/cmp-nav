package com.ostreet.cmp.cmpnav.ui.route.logged_out.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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


const val signUpScreenRoute = "signUpScreenRoute"

val signUpScreenRouter = NavDestRouter(
    name = signUpScreenRoute,
    routeBuilder = { SignUpScreen() }
)

class SignUpScreen : NavDestination(signUpScreenRoute) {

    private val viewModel: SignUpViewModel = scope.get()

    @Composable
    override fun Route(navController: NavController?, modifier: Modifier) {
        val uiState by viewModel.uiState.collectAsState()
        Screen(
            uiState = uiState,
            modifier = modifier,
            toggleLocalBoolean = viewModel::toggleLocalBoolean,
            toggleRemoteBoolean = viewModel::toggleRemoteBoolean,
            navigateBack = { navController?.onBackPressed() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        uiState: SignUpUiState,
        modifier: Modifier,
        toggleLocalBoolean: () -> Unit = {},
        toggleRemoteBoolean: () -> Unit = {},
        navigateBack: () -> Unit = {},
    ) {

        Scaffold { contentPadding ->
            Modifier.padding(contentPadding)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Sign Up Screen")

                BasicButton(
                    "Back to Sign In",
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