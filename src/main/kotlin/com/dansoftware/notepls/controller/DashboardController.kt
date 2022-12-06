package com.dansoftware.notepls.controller

import com.dansoftware.notepls.domain.Note
import com.dansoftware.notepls.service.NoteService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class DashboardController(private val noteService: NoteService) {
    @GetMapping
    fun index(model: Model): String {
        model["notes"] = noteService.getNotes()
        return "pages/notes_dashboard"
    }
}