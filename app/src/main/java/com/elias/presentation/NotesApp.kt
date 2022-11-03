package com.elias.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.elias.presentation.navigation.NotesNavGraph
import com.elias.presentation.navigation.NotesNavigationActions

@Composable
fun NotesApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NotesNavigationActions(navController)
    }

    NotesNavGraph(
        navigationActions = navigationActions,
        navController = navController
    )
}
