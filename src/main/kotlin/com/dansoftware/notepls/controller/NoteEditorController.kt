package com.dansoftware.notepls.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class NoteEditorController {
    @RequestMapping("newnote")
    fun getPage(): String {

        return "pages/note_editor.html"
    }
}