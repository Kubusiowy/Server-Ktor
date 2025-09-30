package com.firek

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.firek.database.Categories
import com.firek.database.Criteria
import com.firek.database.DTO.CategoriesRequestDTO
import com.firek.database.DTO.CriteriaRequestDTO
import com.firek.database.DTO.JuryRequestDTO
import com.firek.database.DTO.ParticipantRequestDTO
import com.firek.database.DTO.toCategoriesResponseDTO
import com.firek.database.DTO.toCriteriaResponseDTO
import com.firek.database.DTO.toJuryResponseDTO
import com.firek.database.DTO.toParticipantsResponseDTO
import com.firek.database.Jury
import com.firek.database.Participants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
                get { call.respondText("OK") }


                route("/participants"){
                        get {
                            val participants = transaction {
                                Participants
                                    .selectAll()
                                    .map{it.toParticipantsResponseDTO()}
                            }
                            call.respond(participants)
                         }

                        get("/{id}") {
                        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest,
                            ErrorResponse(404,
                                "Not found","invalid id parameter"))


                            val row = transaction {
                                Participants
                                    .select{ Participants.participantId eq id }
                                    .singleOrNull()
                            }
                           if (row == null) {
                               call.respond(HttpStatusCode.BadRequest,ErrorResponse(404,"not found","participant $id doesn't exists"))
                           }else{
                               call.respond(row.toParticipantsResponseDTO())
                           }
                        }

                    post {
                        val body = call.receive<ParticipantRequestDTO>()
                               if(body.firstName.isBlank()) throw ValidationException("First name is blank")
                                if(body.lastName.isBlank()) throw ValidationException("Last name is blank")

                            val newId = transaction{
                                Participants.insert {
                                    it[firstName] = body.firstName
                                    it[lastName] = body.lastName
                                    it[schoolId] = body.schoolId
                                }get Participants.participantId
                            }
                            call.respond(HttpStatusCode.Created,"added participant ID $newId")

                        }

                }

                route("/jury"){
                    get{
                            val jury = transaction {
                                Jury
                                    .selectAll()
                                .map{it.toJuryResponseDTO()}
                            }
                        call.respond(jury)
                    }

                    post{
                        val juryPost = call.receive<JuryRequestDTO>()
                            if(juryPost.firstName.isBlank()) throw ValidationException("First name is blank")
                            if(juryPost.lastName.isBlank()) throw ValidationException("Last name is blank")
                        val newId = transaction{
                            Jury.insert {
                                it[firstName] = juryPost.firstName
                                it[lastName] = juryPost.lastName
                            }get Jury.juryId
                        }

                        call.respond(HttpStatusCode.Created,"$newId")
                    }
                }

                route("/categories"){
                    get{
                        val categories = transaction {
                            Categories
                            .selectAll()
                                .map{it.toCategoriesResponseDTO()}
                        }
                        call.respond(categories)
                    }

                    post{
                       val categoriesBody = call.receive<CategoriesRequestDTO>()

                        if(categoriesBody.name.isBlank()) throw ValidationException("First name is blank")
                        val newId = transaction{
                            Categories.insert {
                                it[name] = categoriesBody.name
                            }get Categories.categoryId
                        }
                        call.respond(HttpStatusCode.Created,"$newId")
                    }
                }

                route("/criteria"){
                    get{
                        val criteria = transaction {
                            Criteria
                                .selectAll()
                                .map { it.toCriteriaResponseDTO() }
                        }
                        call.respond(criteria)
                    }

                    post{
                        val criteriaBody = call.receive<CriteriaRequestDTO>()
                        if(criteriaBody.categoryId <= 0) throw ValidationException("Category id is invalid")
                        val newId = transaction{
                            Criteria
                            .insert {
                                it[categoryId] = criteriaBody.categoryId
                                it[name] = criteriaBody.name
                                it[maxPoints] = criteriaBody.maxPoints

                            }get Criteria.CriterionId
                        }
                        call.respond(HttpStatusCode.Created,"$newId")
                    }
                }


        }
    }

       

}
