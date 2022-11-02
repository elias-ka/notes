package com.elias.data.repository

import com.elias.data.data_source.NoteDao
import com.elias.domain.model.Note
import com.elias.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotesStream(): Flow<List<Note>> {
        return dao.getNotesStream()
    }

    override fun getNoteStream(id: Int): Flow<Note> {
        return dao.getNoteStream(id)
    }

    override suspend fun pinNotes(vararg noteIds: Int) {
        dao.pinNote(*noteIds)
    }

    override suspend fun unpinNotes(vararg noteIds: Int) {
        dao.unpinNotes(*noteIds)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}