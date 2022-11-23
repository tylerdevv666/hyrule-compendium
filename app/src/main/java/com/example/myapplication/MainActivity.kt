package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.compendium.CompendiumScreen
import com.example.myapplication.ui.screens.entrydetail.EntryDetailScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "compendium_screen"
                ) {
                    composable("compendium_screen") {
                        CompendiumScreen(navController = navController)
                    }
                    composable(
                        "entry_detail_screen/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val entryId: Int = remember {
                            it.arguments?.getInt("id") ?: 0
                        }

                        EntryDetailScreen(
                            navController = navController,
                            entryId = entryId
                        )
                    }
                }
            }
        }
    }
}