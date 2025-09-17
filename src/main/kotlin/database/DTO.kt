package com.firek.database

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class ParticipantDTO(
    val id: Int,
    val firstName: String,
    val lastName:String,
    val schoolId: Int? = null
)

fun ResultRow.toParticipantDTO() = ParticipantDTO(
    id = this[Participants.participantId],
    firstName = this[Participants.firstName],
    lastName = this[Participants.lastName],
    schoolId = this[Participants.schoolId]
)