package com.elias.presentation.note_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val noteRepository: NoteRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailState())
    val uiState = _uiState.asStateFlow()

    var noteTitle by mutableStateOf("")
        private set

    var noteContent by mutableStateOf("")
        private set

    var isNotePinned by mutableStateOf(false)
        private set

    init {
        savedStateHandle.getStateFlow("noteId", -1).let { existingNoteId ->
            if (existingNoteId.value == -1) return@let

            viewModelScope.launch {
                noteRepository.getNoteStream(existingNoteId.value).collect { note ->
                    @Suppress("SENSELESS_COMPARISON") if (note != null) {
                        _uiState.update {
                            it.copy(
                                noteId = note.id,
                                timestamp = note.timestamp,
                                shouldFocusNoteContentField = note.content.isEmpty()
                            )
                        }
                        isNotePinned = note.isPinned
                        noteTitle = note.title
                        noteContent = note.content
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.OnTitleChange -> {
                noteTitle = event.value
            }

            is NoteDetailEvent.OnContentChange -> {
                noteContent = event.value
            }

            is NoteDetailEvent.OnPinToggle -> {
                isNotePinned = event.value
            }

            is NoteDetailEvent.SaveNote -> {
                val isNewNote = uiState.value.noteId == null
                if (isNewNote && noteTitle.isEmpty() && noteContent.isEmpty()) {
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
                    title = noteTitle,
                    content = noteContent,
                    isPinned = isNotePinned,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            noteRepository.deleteNotes(_uiState.value.noteId!!)
        }
    }
}

