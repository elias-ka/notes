package com.elias.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val id: Int?,
    val title: String,
    val content: String,
    val timestamp: Long
)
