package com.elias.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elias.domain.model.Note
import com.elias.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailState())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.getStateFlow("noteId", -1).let { existingNoteId ->
            if (existingNoteId.value == -1)
                return@let

            viewModelScope.launch {
                noteRepository.getNoteStream(existingNoteId.value).collect { note ->
                    @Suppress("SENSELESS_COMPARISON")
                    if (note != null) {
                        _uiState.update {
                            it.copy(
                                noteId = note.id,
                                noteTitle = note.title,
                                noteContent = note.content,
                                isNotePinned = note.isPinned,
                                timestamp = note.timestamp,
                                shouldFocusNoteContentField = note.content.isEmpty()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.OnTitleChange -> {
                _uiState.update {
                    it.copy(
                        noteTitle = event.value,
                    )
                }
            }
            is NoteDetailEvent.OnContentChange -> {
                _uiState.update {
                    it.copy(
                        noteContent = event.value,
                    )
                }
            }
            is NoteDetailEvent.OnPinToggle -> {
                _uiState.update { it.copy(isNotePinned = event.value) }
            }
            is NoteDetailEvent.SaveNote -> {
                val isNewNote = uiState.value.noteId == null
                if (isNewNote && uiState.value.noteTitle.isEmpty() && uiState.value.noteContent.isEmpty()) {
                    deleteNote()
                } else {
                    saveNote()
                }
            }
            is NoteDetailEvent.DeleteNote -> deleteNote()

        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            noteRepository.insertNote(
                Note(
                    id = uiState.value.noteId,
                    title = uiState.value.noteTitle,
                    content = uiState.value.noteContent,
                    isPinned = uiState.value.isNotePinned,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            noteRepository.deleteNote(
                Note(
                    id = uiState.value.noteId,
                    title = uiState.value.noteTitle,
                    content = uiState.value.noteContent,
                    isPinned = uiState.value.isNotePinned,
                    timestamp = uiState.value.timestamp
                )
            )
        }
    }
}

