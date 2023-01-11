package com.dansoftware.notepls.domain

import java.time.LocalDateTime
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table
import kotlin.properties.Delegates

@Entity
@Table(name = "Notes")
class Note(
        var title: String? = null,
        @field:Lob var content: String? = null,
        var date: LocalDateTime? = null,
        @field:ElementCollection var tags: List<String>? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}