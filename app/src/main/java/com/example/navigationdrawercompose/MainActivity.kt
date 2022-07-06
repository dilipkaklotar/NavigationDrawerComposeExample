package com.example.navigationdrawercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationdrawercompose.ui.screen.*
import com.example.navigationdrawercompose.ui.theme.NavigationDrawerComposeExampleTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerComposeExampleTheme {
                HomeScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreen() {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,

        topBar = {
            AppBar(scaffoldState, scope)
        },

        bottomBar = {
            BottomBar()
        },

        drawerContent = {
            DrawerContent(scope, scaffoldState, navController)
        },
        drawerGesturesEnabled = false,

        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* ... */ },
                backgroundColor = Color.Black
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = "Yoga",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        Navigation(navController = navController)
    }
}


@Composable
fun AppBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    return TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) {
                Icon(Icons.Default.Menu, "Home", tint = Color.Black)
            }
        }
    )
}


@Composable
fun DrawerContent(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val items = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.Music,
        NavDrawerItem.Movies,
        NavDrawerItem.Books,
        NavDrawerItem.Profile,
        NavDrawerItem.Settings
    )


    Column() {

        // Header
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .padding(all = 16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_supervised_user_circle_24),
                contentDescription = R.drawable.ic_baseline_supervised_user_circle_24.toString(),
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(Color.Black),
            )

        }
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Developed by Dilip Kaklotar",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    DrawerContent(scope = scope, scaffoldState = scaffoldState, navController = navController)
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
            MainScreen()
        }
        composable(NavDrawerItem.Music.route) {
            MusicScreen()
        }
        composable(NavDrawerItem.Movies.route) {
            MoviesScreen()
        }
        composable(NavDrawerItem.Books.route) {
            BooksScreen()
        }
        composable(NavDrawerItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
    }
}


@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    val background = if (selected) R.color.black else android.R.color.transparent
    val textColor = if (selected) Color.White else Color.Black;

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(textColor),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(25.dp)
                .width(25.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 14.sp,
            color = textColor
        )
    }
}


@Composable
fun BottomBar() {

    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.Black,

        ) {

        BottomNavigationItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_home_24),
                contentDescription = "Home",
                tint = Color.Black
            )
        },
            label = { Text(text = "Home") },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
            })



        BottomNavigationItem(icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_person_2_24),
                contentDescription = "Profile",
                tint = Color.Black
            )
        },
            label = { Text(text = "Profile") },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
            })
    }
}
