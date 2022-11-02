package com.elias.domain.repository

import com.elias.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesStream(): Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note>

    suspend fun pinNotes(vararg noteIds: Int)

    suspend fun unpinNotes(vararg noteIds: Int)

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}