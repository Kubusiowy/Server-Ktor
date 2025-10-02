package com.firek.database.DTO

import kotlinx.serialization.Serializable

@Serializable
data class ScoresResponseDTO(
    val jurorId:Int,
    val participantID:Int,
    val criteriaId:Int,
    val points: Int
)