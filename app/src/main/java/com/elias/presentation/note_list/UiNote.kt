package com.elias.presentation.note_list

import com.elias.domain.model.Note

data class UiNote(
    val id: Int? = null,
    val title: String,
    val content: String,
    val isPinned: Boolean,
    val timestamp: Long,
) {
    companion object {
        fun fromDomainNote(note: Note): UiNote {
            return UiNote(
                id = note.id,
                title = note.title,
                content = note.content,
                isPinned = note.isPinned,
                timestamp = note.timestamp
            )
        }
    }
}

