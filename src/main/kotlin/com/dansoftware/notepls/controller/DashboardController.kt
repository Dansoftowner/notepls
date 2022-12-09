package com.dansoftware.notepls.controller

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
    fun allNotes(@RequestParam("tags") tags: List<String>?, model: Model): String {
        model["values"] = object {
            val tagsList = tags
            val notesList = noteService.getAllNotes(tags)
        }
        return "pages/notes_dashboard"
    }

}