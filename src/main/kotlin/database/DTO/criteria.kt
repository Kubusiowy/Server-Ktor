package com.firek.database.DTO

import com.firek.database.Categories
import com.firek.database.Criteria
import com.firek.database.Criteria.maxPoints
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class CriteriaResponseDTO(
     val id:Int,
    val categoryId:Int,
    val name:String,
    val maxPoints:Int
)

@Serializable
data class CriteriaRequestDTO(
    val categoryId:Int,
    val name:String,
    val maxPoints:Int
)

fun ResultRow.toCriteriaResponseDTO() = CriteriaResponseDTO (
   id = this[Criteria.CriterionId],
    categoryId = this[Criteria.categoryId],
    name = this[Criteria.name],
    maxPoints = this[Criteria.maxPoints],
)
