package com.dansoftware.notepls.controller

import com.dansoftware.notepls.domain.Note
import com.dansoftware.notepls.service.DashboardService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class DashboardController(private val dashboardService: DashboardService) {
    @GetMapping
    fun index(model: Model): String {
        model["notes"] = dashboardService.getNotes()
        return "pages/notes_dashboard"
    }
}