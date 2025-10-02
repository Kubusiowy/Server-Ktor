package com.firek.database


import org.jetbrains.exposed.sql.Table

// 1/7
object Admin: Table("admin") {
    val adminId = integer("admin_id").autoIncrement()
    val login = varchar("login", 255).uniqueIndex()
    val hashPassword = varchar("hash_password", 255)
    override val primaryKey = PrimaryKey(adminId)
}

// 2/7
object Schools: Table("schools") {
    val schoolId = integer("school_id").autoIncrement()
    val name = varchar("school_name", 255)
    val city = varchar("city", 255)
    override val primaryKey = PrimaryKey(schoolId)
}
// 3/7
object Jury: Table("jurors"){
    val juryId = integer("juror_id").autoIncrement()
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    override val primaryKey = PrimaryKey(juryId)
}
// 4/7
object Categories: Table("categories") {
    val categoryId = integer("category_id").autoIncrement()
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(categoryId)
}
// 5/7
object Participants: Table("participants"){
    val participantId = integer("participant_id").autoIncrement()
    val firstName = varchar("first_name", 100)
    val lastName = varchar("last_name", 255)
    val schoolId = integer("school_id")
        .references(Schools.schoolId)
        .nullable()
    override val primaryKey = PrimaryKey(participantId)
}

// 6/7
object Criteria: Table("criteria"){
    val CriterionId = integer("criterion_id").autoIncrement()
    val categoryId = integer("category_id").references(Categories.categoryId)
    val name = varchar("name", 120)
    val maxPoints = integer("max_points").default(10)
    override val primaryKey = PrimaryKey(CriterionId)
}

// 7/7
object Scores : Table("scores") {
    val scoreId       = integer("score_id").autoIncrement()
    val jurorId       = integer("juror_id").references(Jury.juryId)
    val participantId = integer("participant_id").references(Participants.participantId)
    val criteriaId   = integer("criterion_id").references(Criteria.CriterionId)
    val points        = integer("points")
    override val primaryKey = PrimaryKey(scoreId)
}