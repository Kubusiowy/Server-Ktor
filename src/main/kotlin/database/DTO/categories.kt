package com.firek.database.DTO

import com.firek.database.Categories
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class CategoriesResponseDTO(
    val id:Int,
    val name:String,
)

@Serializable
data class CategoriesRequestDTO(
    val name:String,
)

fun ResultRow.toCategoriesResponseDTO() = CategoriesResponseDTO (
    id = this[Categories.categoryId],
    name = this[Categories.name]
)