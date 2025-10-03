package com.firek.database.DTO

import com.firek.database.Jury
import com.firek.database.Scores
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class ScoresResponseDTO(
    val scoreId:Int,
    val jurorId:Int,
    val participantId:Int,
    val criteriaId:Int,
    val points: Int
)

@Serializable
data class ScoresRequestDTO(
    val jurorId:Int,
    val participantId:Int,
    val criteriaId:Int,
    val points: Int
)

fun ResultRow.toScoresResponseDTO() = ScoresResponseDTO(
    scoreId = this[Scores.scoreId],
    jurorId = this[Scores.jurorId],
    participantId = this[Scores.participantId],
    criteriaId = this[Scores.criteriaId],
    points = this[Scores.points]
)