package com.dansoftware.notepls.controller

import com.dansoftware.notepls.domain.Note
import com.dansoftware.notepls.service.NoteService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/")
class DashboardController(private val noteService: NoteService) {
    @GetMapping
    fun index(model: Model): String {
        return "redirect:/all"
    }

    @GetMapping("all")
    fun allNotes(model: Model): String {
        model["values"] = mapOf(null to noteService.getAllNotes())
        return "pages/notes_dashboard"
    }

    @GetMapping("all/tags")
    fun allNotesByTags(@RequestParam("custom") customTags: List<String>?, model: Model): String {
        model["values"] = when(customTags) {
            null -> noteService.getAllNotesByTags()
            else -> mapOf(customTags to noteService.getAllNotes(customTags))
        }
        return "pages/notes_dashboard"
    }
}