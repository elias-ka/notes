package com.elias.presentation.note_detail

sealed class NoteDetailEvent {
    data class OnTitleChange(val value: String) : NoteDetailEvent()
    data class OnContentChange(val value: String) : NoteDetailEvent()
    data class OnPinToggle(val value: Boolean) : NoteDetailEvent()
    object SaveNote : NoteDetailEvent()
    object DeleteNote : NoteDetailEvent()
}