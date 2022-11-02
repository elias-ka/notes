package com.elias.presentation.note_detail

data class NoteDetailState(
    val noteId: Int? = null,
    val noteTitle: String = "",
    val noteContent: String = "",
    val isNotePinned: Boolean = false,
    val timestamp: Long = 0,
    val shouldFocusNoteContentField: Boolean = false
)