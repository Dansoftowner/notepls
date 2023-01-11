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

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
class NoteController(private val service: NoteService) {

    @GetMapping("/")
    fun index(model: Model): String {
        return "redirect:/all"
    }

    @GetMapping("/all")
    fun allNotes(model: Model): String {
        model["values"] = mapOf(null to service.getAllNotes())
        return "pages/notes_dashboard"
    }

    @GetMapping("/all/tags")
    fun allNotesByTags(@RequestParam("custom") customTags: List<String>?, model: Model): String {
        model["values"] = when(customTags) {
            null -> service.getAllNotesByTags()
            else -> mapOf(customTags to service.getAllNotes(customTags))
        }
        return "pages/notes_dashboard"
    }

    @GetMapping("/new")
    fun getPage(): String {
        return "pages/note_editor.html"
    }

    @PostMapping("/new")
    fun createNewNote(
            @RequestParam("title") title: String,
            @RequestParam("content") content: String,
            @RequestParam("tags") tags: List<String>?,
            model: Model
    ): String {
        val newNote = Note(
                title = title,
                content = content,
                tags = tags,
                date = LocalDateTime.now()
        )
        service.insertNote(newNote)
        return "redirect:/edit/${newNote.id}"
    }

    @GetMapping("/edit/{id}")
    fun getPage(@PathVariable("id") id: Long, model: Model): String {
        service.getNoteById(id)?.let { model["note"] = it }
        return "pages/note_editor.html"
    }

    @PostMapping("/edit/{id}")
    fun submitChanges(
            @PathVariable("id") id: Long,
            @RequestParam("title") title: String,
            @RequestParam("content") content: String,
            @RequestParam("tags") tags: List<String>?,
            @RequestParam("method") method: String?,
            model: Model
    ): String {
        return when (method) {
            "DELETE" -> {
                service.removeNote(id)
                return "redirect:/all"
            }
            else -> {
                service.updateNote(Note(title, content, LocalDateTime.now(), tags).apply { this.id = id })
                getPage(id, model)
            }
        }
    }
}