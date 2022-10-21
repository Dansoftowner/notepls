package com.dansoftware.notepls.domain

import java.time.LocalDateTime

class Note(
        val title: String,
        val content: String,
        val date: LocalDateTime,
        val category: String?
)