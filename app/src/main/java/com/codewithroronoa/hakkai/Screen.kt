package com.codewithroronoa.hakkai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val title: String,
                     val route : String,
                     val selectedIcon: ImageVector,
                     val unselectedIcon: ImageVector){
    object Home : Screen(title = "Home", route = "home_screen", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    object Profile : Screen(title = "Chat", route = "profile_screen", selectedIcon = Icons.Filled.Email, unselectedIcon = Icons.Outlined.Email)
    object Calendar : Screen(title = "Calendar", route = "calendar_screen", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings)
}