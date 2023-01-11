/*
 * This file is part of Notepls.
 * Copyright (c) 2023 Daniel Gyoerffy.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.dansoftware.notepls.note

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collections.singletonList

@Service
@Configuration
class NoteService(private val repository: NoteRepository) {

    /**
     * Retrieves all notes stored in the database.
     * > Note: these notes' content is abbreviated to reduce insufficient network traffic.
     */
    fun getAllNotes(tags: List<String>? = null): List<Note> {
        val records = repository.findAll()
        return tags?.let {
            records.filter { it.tags?.containsAll(tags) ?: false }
        } ?: records.toList()
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
        repository.save(newNote)
    }

    /**
     * Updates the given note.
     * It won't work if the given note object doesn't have an id.
     *
     * The given [note] object's attributes will be changed accordingly.
     */
    fun updateNote(note: Note) {
        repository.save(note)
    }

    fun getAllNotesByTags(): Map<List<String>?, List<Note>> {
        val map = mutableMapOf<List<String>?, MutableList<Note>>()
        repository.findAll().forEach { note ->
            note.tags?.forEach {
                map[singletonList(it)]?.add(note) ?: run {
                    map[singletonList(it)] = mutableListOf(note)
                }
            }
        }
        return map.toMap()
    }

    fun removeNote(id: Long) {
        repository.deleteById(id)
    }

    // TODO: remove
    @Bean
    fun initialRecords() = CommandLineRunner {
        repository.saveAll(
                mutableListOf(
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
        )
    }
}