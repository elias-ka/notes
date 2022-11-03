package com.elias.presentation.note_detail

data class NoteDetailState(
    val noteId: Int? = null,
    val timestamp: Long = 0,
    val shouldFocusNoteContentField: Boolean = false
)
