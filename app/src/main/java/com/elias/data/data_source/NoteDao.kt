package com.elias.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elias.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY is_pinned DESC")
    fun getNotesStream(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id ")
    fun getNoteStream(id: Int): Flow<Note>

    @Query("UPDATE notes SET is_pinned = 1 WHERE id IN (:noteIds)")
    suspend fun pinNote(vararg noteIds: Int)

    @Query("UPDATE notes SET is_pinned = 0 WHERE id IN (:noteIds)")
    suspend fun unpinNotes(vararg noteIds: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Query("DELETE FROM notes WHERE id in (:ids)")
    suspend fun deleteNotes(vararg ids: Int)
}
