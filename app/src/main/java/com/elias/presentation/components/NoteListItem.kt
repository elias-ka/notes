package com.elias.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elias.presentation.note_list.UiNote
import com.elias.presentation.theme.NotesShapes
import com.elias.presentation.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListItem(
    note: UiNote,
    modifier: Modifier = Modifier,
    border: BorderStroke = CardDefaults.outlinedCardBorder(),
    colors: CardColors = CardDefaults.outlinedCardColors(),
    onNoteClick: (Int) -> Unit = {},
) {
    Card(
        shape = NotesShapes.medium,
        border = border,
        colors = colors,
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp),
        onClick = { onNoteClick(note.id!!) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row {
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
        NoteListItem(note = UiNote(
            id = 0,
            title = "Note title",
            content = "Note content",
            isPinned = true,
            timestamp = 1667131142L
        ), onNoteClick = {})
    }
}
