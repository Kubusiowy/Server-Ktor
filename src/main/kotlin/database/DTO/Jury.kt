package com.firek.database.DTO

import com.firek.database.Jury
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class JuryResponseDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
)

@Serializable
data class JuryRequestDTO(
    val firstName: String,
    val lastName: String,
)

fun ResultRow.toJuryResponseDTO() = JuryResponseDTO(
    id = this[Jury.juryId],
    firstName = this[Jury.firstName],
    lastName = this[Jury.lastName],
)
