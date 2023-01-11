/*
 * This file is part of Notepls.
 * Copyright (c) 2023 Daniel Gyoerffy.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.dansoftware.notepls.note

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