package com.dansoftware.notepls.service

import com.dansoftware.notepls.domain.Note
import com.dansoftware.notepls.domain.NoteRepository
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collections.singletonList

@Service
@Configuration
class NoteService(private val repository: NoteRepository) {

    private val noteList = mutableListOf(
            Note(
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
                    title = "My note 2",
                    content = """
                       ### What is this man
                       > Seems nice btw
                       """.trimIndent(),
                    tags = listOf("Simple", "Test", "Something"),
                    date = LocalDateTime.now()
            ),
            Note(
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
    fun getNoteById(id: Long): Note? {
        return repository.findById(id).orElse(null)
    }

    /**
     * Inserts the given note into the database.
     *
     * After insertion the given [newNote] object's id will be set accordingly.
     */
    fun insertNote(newNote: Note) {
        val newId = (if (noteList.isNotEmpty()) noteList.maxOf { it.id!! } else 0) + 1
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
        val map = mutableMapOf<List<String>?, MutableList<Note>>()
        noteList.forEach { note ->
            note.tags?.forEach {
                map[singletonList(it)]?.add(note) ?: run {
                    map[singletonList(it)] = mutableListOf(note)
                }
            }
        }
        return map.toMap()
    }

    fun removeNote(id: Long) {
        val iterator = noteList.iterator()
        while (iterator.hasNext()) {
            val note = iterator.next()
            if (note.id == id) {
                iterator.remove()
                break
            }
        }
    }

    fun initialRecords() {

    }
}