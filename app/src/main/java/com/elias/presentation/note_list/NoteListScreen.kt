package com.elias.presentation.note_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.NoteAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elias.R
import com.elias.domain.model.Note
import com.elias.presentation.components.NoteListItem
import com.elias.util.isScrolled

@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val notesLazyListState = rememberLazyListState()
    val uiState: NoteListState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.add_note)) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_note)
                    )
                },
                onClick = { onFabClick() },
                expanded = !notesLazyListState.isScrolled
            )
        }
    ) { padding ->
        if (uiState.notes.isEmpty() && !uiState.isLoading) {
            EmptyNoteList()
        } else {
            NoteList(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize(),
                contentPadding = padding,
                notes = uiState.notes,
                notesLazyListState = notesLazyListState,
                onNoteClick = { onNoteClick(it) },
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    notes: List<Note>,
    notesLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onNoteClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = notesLazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = contentPadding
    ) {
        items(items = notes, key = { it.id!! }) { note ->
            NoteListItem(
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 250)),
                note = note,
                onNoteClick = onNoteClick,
            )
        }

    }
}

@Composable
fun EmptyNoteList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.NoteAdd,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            stringResource(R.string.empty_note_list_message),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}