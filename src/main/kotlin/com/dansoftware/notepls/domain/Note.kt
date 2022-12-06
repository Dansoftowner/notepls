package com.dansoftware.notepls.domain

import java.time.LocalDateTime
import kotlin.properties.Delegates

class Note(
        id: Int? = null,
        var title: String?,
        var content: String?,
        var date: LocalDateTime,
        var tags: List<String>?
) {
    var id by Delegates.notNull<Int>()

    init {
        id?.let(this::id::set)
    }
}