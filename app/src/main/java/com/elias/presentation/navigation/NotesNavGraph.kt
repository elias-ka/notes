package com.elias.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elias.presentation.note_detail.NoteDetailScreen
import com.elias.presentation.note_list.NoteListScreen

@Composable
fun NotesNavGraph(
    navigationActions: NotesNavigationActions,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NotesDestinations.NOTES_ROUTE
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NotesDestinations.NOTES_ROUTE) {
            NoteListScreen(
                onNoteClick = { noteId -> navigationActions.navigateToDetail(noteId) },
                onFabClick = { navigationActions.navigateToDetail(-1) })
        }
        composable(route = "${NotesDestinations.NOTE_DETAIL_ROUTE}/{noteId}", arguments = listOf(
            navArgument("noteId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )) {
            NoteDetailScreen(
                modifier = modifier,
                onNavigationIconClick = { navController.navigateUp() })
        }
    }
}