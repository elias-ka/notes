package com.elias.presentation.note_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elias.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val _notes = MutableStateFlow(emptyList<UiNote>())
    val notes = _notes.asStateFlow()

    var isLoading = mutableStateOf(true)
        private set

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            noteRepository.getNotesStream()
                .onEach { isLoading.value = false }
                .collect { notes ->
                    _notes.update { notes.map { UiNote.fromDomainNote(it) } }
                }
        }
    }
}
