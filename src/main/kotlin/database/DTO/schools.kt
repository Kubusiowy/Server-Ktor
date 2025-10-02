package com.firek.database.DTO

import com.firek.database.Schools
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class SchoolsResponseDTO(
    val id:Int,
    val name:String,
    val city:String
)

@Serializable
data class SchoolRequestDTO(
    val name:String,
    val city:String,
)

fun ResultRow.toSchoolsResponseDTO() = SchoolsResponseDTO (
    id = this[Schools.schoolId],
    name = this[Schools.name],
    city = this[Schools.city]
)