package com.elias.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elias.domain.model.Note
import com.elias.presentation.theme.NotesShapes
import com.elias.presentation.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListItem(
    note: Note,
    modifier: Modifier = Modifier,
    cardColors: CardColors = CardDefaults.outlinedCardColors(),
    borderStroke: BorderStroke = CardDefaults.outlinedCardBorder(),
    onNoteClick: (Int) -> Unit
) {
    Card(
        shape = NotesShapes.medium,
        border = borderStroke,
        colors = cardColors,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        onClick = { onNoteClick(note.id!!) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.semantics(mergeDescendants = true, properties = {})
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 6.dp),
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (note.isPinned) {
                    Icon(
                        modifier = Modifier.rotate(45f),
                        imageVector = Icons.Filled.PushPin,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
            if (note.content.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = note.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteListItemPreview() {
    NotesTheme(darkTheme = true) {
        NoteListItem(note = Note(
            id = 0,
            title = "Note title",
            content = "Note content",
            isPinned = true,
            timestamp = 1667131142L
        ), onNoteClick = {})
    }
}