package com.dansoftware.notepls.domain

import org.springframework.data.repository.CrudRepository

interface NoteRepository : CrudRepository<Note, Long> {
}