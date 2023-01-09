package com.dansoftware.notepls.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.properties.Delegates

@Entity
class Note(
        var title: String?,
        var content: String?,
        var date: LocalDateTime,
        var tags: List<String>?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}