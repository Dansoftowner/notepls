package com.dansoftware.notepls.service

import com.dansoftware.notepls.domain.Note
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class NoteService {

    private val noteList = mutableListOf(
            Note(
                    id = 1,
                    title = "My note",
                    content = """
                       # My freaking note
                       * Yes note
                       * No note
                    """.trimIndent(),
                    tags = listOf("Nonsense", "Craziness"),
                    date = LocalDateTime.of(LocalDate.of(2004, 11, 1), LocalTime.of(10, 10))
            ),
            Note(
                    id = 2,
                    title = "My note 2",
                    content = """
                       ### What is this man
                       > Seems nice btw
                       """.trimIndent(),
                    tags = listOf("Simple", "Test", "Something"),
                    date = LocalDateTime.now()
            ),
            Note(
                    id = 3,
                    title = "Shopping list",
                    content = """
                       * Toothbrush
                       * Shampoo
                       * Bread 
                       
                       ${(1..100).joinToString("\n")}
                       """.trimIndent(),
                    tags = listOf("list", "important"),
                    date = LocalDateTime.of(2005, 5, 23, 12, 0)
            )
    )

    /**
     * Retrieves all notes stored in the database.
     * > Note: these notes' content is abbreviated to reduce insufficient network traffic.
     */
    fun getAllNotes(tags: List<String>? = null): List<Note> {
        return tags?.let {
            noteList.filter { it.tags?.containsAll(tags) ?: false }
        } ?: noteList.toList()
    }

    /**
     * Retrieves the note with the given ID.
     */
    fun getNoteById(id: Int): Note? {
        return noteList.find { it.id == id }
    }

    /**
     * Inserts the given note into the database.
     *
     * After insertion the given [newNote] object's id will be set accordingly.
     */
    fun insertNote(newNote: Note) {
        val newId = noteList.maxOf { it.id } + 1
        newNote.id = newId
        noteList.add(newNote)
    }

    /**
     * Updates the given note.
     * It won't work if the given note object doesn't have an id.
     *
     * The given [note] object's attributes will be changed accordingly.
     */
    fun updateNote(note: Note) {
        noteList.find { it.id == note.id }?.let {
            it.title = note.title
            it.content = note.content
            it.tags = note.tags
        }
    }

    fun getAllNotesByTags(): Map<List<String>?, List<Note>> {
        return noteList.groupBy { it.tags }
    }
}