package com.example.cleanarchitectureplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarchitectureplayground.ui.screen.todolist.ToDoListDetailScreen
import com.example.cleanarchitectureplayground.ui.screen.todolist.ToDoListScreen
import com.example.cleanarchitectureplayground.ui.screen.weather.WeatherForecastScreen
import com.example.cleanarchitectureplayground.ui.screen.weather.WeatherScreen
import com.example.cleanarchitectureplayground.ui.theme.CleanArchitecturePlaygroundTheme
import com.example.cleanarchitectureplayground.ui.utils.ButtonItem
import com.example.cleanarchitectureplayground.ui.utils.Buttons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitecturePlaygroundTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            ButtonListScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("todo_list") {
                            ToDoListScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(
                            route = "todo_list_detail/{itemId}",
                            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            ToDoListDetailScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                itemId = backStackEntry.arguments?.getString("itemId")
                            )
                        }
                        composable("weather") {
                            WeatherScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("weather_forecast") {
                            WeatherForecastScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(Buttons.buttons) { button ->
            CustomDashboardButton(
                button = button,
                onClick = {
                    when (button.id) {
                        1 -> navController.navigate("todo_list")
                        2 -> navController.navigate("weather")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CustomDashboardButton(
    button: ButtonItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE),
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = button.iconId),
                contentDescription = "${button.title} icon",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
            Text(
                text = button.title,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}