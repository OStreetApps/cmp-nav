package com.ostreet.cmp.cmpnav.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ostreet.cmp.cmpnav.ui.designsystem.SupportedIcons
import com.ostreet.cmp.cmpnav.ui.route.logged_in.friends.friendsGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.home.homeGraph
import com.ostreet.cmp.cmpnav.ui.route.logged_in.settings.settingsGraph

@Composable
fun BottomNavBar(
    bottomNavIcons: List<BottomNavIcon>,
    onNavigateToGraph: (BottomNavIcon) -> Unit,
    currentGraph: String,
    bottomNavBadgeState: Map<String, Int>,
    modifier: Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        bottomNavIcons.forEach { bottomNavIcon ->
            val selected = currentGraph.isGraphRoute(bottomNavIcon)
            val badgeCount = bottomNavBadgeState[bottomNavIcon.name] ?: 0
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToGraph(bottomNavIcon) },
                icon = {
                    val icon = if (selected) {
                        bottomNavIcon.selectedIcon
                    } else {
                        bottomNavIcon.unselectedIcon
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                },
                iconContentDesc = bottomNavIcon.iconTextId,
                badgeContentDesc = bottomNavIcon.badgeTextId,
                badgeCount = badgeCount
            )
        }
    }

}

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    iconContentDesc: String,
    badgeContentDesc: String,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    badgeCount: Int = 0,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    this@NavigationBarItem.NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { BottomBarBadgedBox(
            selected = selected,
            icon = { if (selected) selectedIcon() else icon() },
            iconTextId = iconContentDesc,
            badgeTextId = badgeContentDesc,
            badgeCount = badgeCount
        ) },
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        //colors is default right now
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarBadgedBox(
    selected: Boolean,
    icon: @Composable () -> Unit,
    iconTextId: String,
    badgeTextId: String,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    badgeCount: Int = 0
) {

    val badgeModifier = Modifier
        .offset(x = (-6).dp, y = 6.dp)
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.background,
            shape = CircleShape
        )

    val badgeLabel = if (badgeCount > 9) {
        "9+"
    } else {
        badgeCount.toString()
    }
    val badgeContentDesc = "$badgeLabel $badgeTextId"

    BadgedBox(
        badge = {
            if (badgeCount > 0) {
                Badge(modifier = badgeModifier) {
                    Text(modifier = Modifier.semantics { contentDescription = badgeContentDesc },
                        text = badgeLabel)
                }
            }
        }) {
        if (selected) selectedIcon() else icon()
    }
}

enum class BottomNavIcon(
    val graphName: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: String,
    val badgeTextId: String
) {
    HOME(
        graphName = homeGraph,
        selectedIcon = SupportedIcons.home,
        unselectedIcon = SupportedIcons.home,
        iconTextId = "Home",
        badgeTextId = "Home"
    ),
    FRIENDS(
        graphName = friendsGraph,
        selectedIcon = SupportedIcons.heart,
        unselectedIcon = SupportedIcons.heart,
        iconTextId = "Friends",
        badgeTextId = "Friends"
    ),
    SETTINGS(
        graphName = settingsGraph,
        selectedIcon = SupportedIcons.settings,
        unselectedIcon = SupportedIcons.settings,
        iconTextId = "Settings",
        badgeTextId = "Settings"
    )
}

/*private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BottomNavIcon) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
*/

private fun String.isGraphRoute(destination: BottomNavIcon) = this == destination.graphName