package com.elias.presentation.note_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elias.R
import com.elias.presentation.components.TransparentTextField

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLifecycleComposeApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val uiState: NoteDetailState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val (titleFieldFocus, contentFieldFocus) = remember { FocusRequester.createRefs() }

    if (uiState.shouldFocusNoteContentField) {
        LaunchedEffect(key1 = Unit) {
            contentFieldFocus.requestFocus()
        }
    }

    BackHandler {
        viewModel.onEvent(NoteDetailEvent.SaveNote)
        onNavigationIconClick()
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(title = { Text("") }, scrollBehavior = scrollBehavior, navigationIcon = {
            IconButton(onClick = {
                viewModel.onEvent(NoteDetailEvent.SaveNote)
                onNavigationIconClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back)
                )
            }
        }, actions = {
            IconToggleButton(checked = viewModel.isNotePinned,
                onCheckedChange = { viewModel.onEvent(NoteDetailEvent.OnPinToggle(it)) }) {
                Icon(
                    imageVector = if (viewModel.isNotePinned) Icons.Default.PushPin else Icons.Outlined.PushPin,
                    contentDescription = if (viewModel.isNotePinned) stringResource(R.string.cd_unpin)
                    else stringResource(R.string.cd_pin)
                )
            }
            if (uiState.noteId != null) {
                IconButton(onClick = { viewModel.onEvent(NoteDetailEvent.DeleteNote); onNavigationIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.cd_delete)
                    )
                }
            }
        })
    }

    ) { padding ->
        // TODO: fix keyboard overlapping the text while still having verticalScroll modifier on the column,
        //       if Google ever fixes it. https://issuetracker.google.com/issues/192043120
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(padding)
        ) {
            TransparentTextField(
                modifier = Modifier
                    .focusRequester(titleFieldFocus)
                    .focusProperties { next = contentFieldFocus },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                text = { viewModel.noteTitle },
                onValueChange = { viewModel.onEvent(NoteDetailEvent.OnTitleChange(it)) },
                placeholder = R.string.note_title_placeholder,
                textStyle = MaterialTheme.typography.titleLarge
            )
            TransparentTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(contentFieldFocus)
                    .focusProperties { previous = titleFieldFocus },
                text = { viewModel.noteContent },
                onValueChange = { viewModel.onEvent(NoteDetailEvent.OnContentChange(it)) },
                placeholder = R.string.note_content_placeholder,
            )
        }
    }
}
