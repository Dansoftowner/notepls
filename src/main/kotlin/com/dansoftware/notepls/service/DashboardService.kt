package com.dansoftware.notepls.service

import com.dansoftware.notepls.domain.Note
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class DashboardService {
    fun getNotes(): List<Note> {
        return listOf(
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
    }
}