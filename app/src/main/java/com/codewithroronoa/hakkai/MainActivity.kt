package com.codewithroronoa.hakkai

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codewithroronoa.hakkai.Calendar.CalendarScreen
import com.codewithroronoa.hakkai.Home.HomeScreen
import com.codewithroronoa.hakkai.Home.SeasonalAnime
import com.codewithroronoa.hakkai.Profile.ProfileScreen
import com.codewithroronoa.hakkai.ui.theme.HakkaiTheme
import com.codewithroronoa.hakkai.utils.Constants.CLIENT_ID
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import timber.log.Timber

data class BottomNavigationItem(
    val title: String,
    val route : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HakkaiTheme {
                val navController = rememberNavController()
                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        route = "home_screen",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    BottomNavigationItem(
                        title = "Chat",
                        route = "calendar_screen",
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email,

                    ),
                    BottomNavigationItem(
                        title = "Settings",
                        route = "profile_screen",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "HAKKAI",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(Color.DarkGray))
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                        ) {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        // navController.navigate(item.title)
                                        navController.navigate(item.route){
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    label = {
                                        Text(text = item.title, color = Color.White)
                                    },
                                    alwaysShowLabel = true,
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) {
                    val padding = it
                    Surface(
                        modifier = Modifier.padding(padding),
                    ) {
                        NavHost(navController = navController, startDestination = "home_screen"){
                            composable("home_screen"){
                                HomeScreen()
                            }
                            composable("calendar_screen"){
                                CalendarScreen()
                            }
                            composable("profile_screen"){
                                ProfileScreen()
                            }
                        }
                    }

                }

//                Column(modifier = Modifier.fillMaxSize()) {
//                    val context = LocalContext.current
//                    LoginScreen(onLoginClick = { startOAuthFlow(context) })
//                }

            }
        }
    }
}


@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onLoginClick() }) {
            Text("Login with OAuth2")
        }
    }
}
fun startOAuthFlow(context: Context) {
    val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse("https://myanimelist.net/v1/oauth2/authorize"),
        Uri.parse("https://myanimelist.net/v1/oauth2/token")
    )
    val clientId = CLIENT_ID
    val redirectUri = Uri.parse("myapp://auth/callback")
    val authRequest = AuthorizationRequest.Builder(
        serviceConfiguration,
        clientId,
        ResponseTypeValues.CODE,Uri.parse("")
    ).build()

    val authService = AuthorizationService(context)
    val authIntent = authService.getAuthorizationRequestIntent(authRequest)
    context.startActivity(authIntent)
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    LoginScreen(onLoginClick = {startOAuthFlow(context)})
}
