package com.firek.database.DTO

import com.firek.database.Participants
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class ParticipantsResponseDTO(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val schoolId:Int? = null,
)
@Serializable
data class ParticipantRequestDTO(
    val firstName:String,
    val lastName:String,
    val schoolId:Int? = null,
)

fun ResultRow.toParticipantsResponseDTO() = ParticipantsResponseDTO (
    id = this[Participants.participantId],
    firstName = this[Participants.firstName],
    lastName = this[Participants.lastName],
    schoolId = this[Participants.schoolId],
)
