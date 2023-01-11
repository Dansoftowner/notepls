package com.dansoftware.notepls.controller

import com.dansoftware.notepls.domain.Note
import com.dansoftware.notepls.service.NoteService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
class NoteEditorController(private val service: NoteService) {
    @GetMapping("new")
    fun getPage(): String {
        return "pages/note_editor.html"
    }

    @PostMapping("new")
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

    @GetMapping("edit/{id}")
    fun getPage(@PathVariable("id") id: Long, model: Model): String {
        service.getNoteById(id)?.let { model["note"] = it }
        return "pages/note_editor.html"
    }

    @PostMapping("edit/{id}")
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