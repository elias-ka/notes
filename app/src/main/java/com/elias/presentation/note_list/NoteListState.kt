package com.elias.presentation.note_list

import com.elias.domain.model.Note


data class NoteListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
)
