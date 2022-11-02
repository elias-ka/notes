package com.elias.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    @ColumnInfo(name = "is_pinned", defaultValue = "0")
    val isPinned: Boolean,
    val timestamp: Long
)
