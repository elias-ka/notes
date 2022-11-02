package com.elias.presentation.navigation

import androidx.navigation.NavHostController

object NotesDestinations {
    const val NOTES_ROUTE = "notes"
    const val NOTE_DETAIL_ROUTE = "noteDetail"
}

class NotesNavigationActions(navController: NavHostController) {
    val navigateToDetail: (Int) -> Unit = { noteId ->
        navController.navigate("${NotesDestinations.NOTE_DETAIL_ROUTE}/$noteId") {
            launchSingleTop = true
        }
    }
}