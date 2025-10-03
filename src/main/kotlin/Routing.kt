package com.firek

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.firek.database.Categories
import com.firek.database.Criteria
import com.firek.database.DTO.CategoriesRequestDTO
import com.firek.database.DTO.CriteriaRequestDTO
import com.firek.database.DTO.JuryRequestDTO
import com.firek.database.DTO.ParticipantRequestDTO
import com.firek.database.DTO.SchoolRequestDTO
import com.firek.database.DTO.toCategoriesResponseDTO
import com.firek.database.DTO.toCriteriaResponseDTO
import com.firek.database.DTO.toJuryResponseDTO
import com.firek.database.DTO.toParticipantsResponseDTO
import com.firek.database.DTO.toSchoolsResponseDTO
import com.firek.database.Jury
import com.firek.database.Participants
import com.firek.database.Schools
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
                get { call.respondText("OK") }

                // uczestnicy 1/6
                route("/participants"){ //status OK
                        get {
                            val participants = transaction {
                                Participants
                                    .selectAll()
                                    .map{it.toParticipantsResponseDTO()}
                            }
                            call.respond(participants)
                         }

                        get("/{id}") { //status OK
                        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest,
                            ErrorResponse(400,
                                "invalid id parameter","id must be an INT"))


                            val row = transaction {
                                Participants
                                    .select{ Participants.participantId eq id }
                                    .singleOrNull()
                            }
                           if (row == null) {
                               call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"not found","participant $id doesn't exists"))
                           }else{
                               call.respond(row.toParticipantsResponseDTO())
                           }
                        }

                    post { // status OK
                        val body = call.receive<ParticipantRequestDTO>()

                               if(body.firstName.isBlank()) throw ValidationException("Participant First name is blank")
                                if(body.lastName.isBlank()) throw ValidationException("Participant Last name is blank")

                            val newId = transaction{
                                Participants.insert {
                                    it[firstName] = body.firstName
                                    it[lastName] = body.lastName
                                    it[schoolId] = body.schoolId
                                }get Participants.participantId
                            }
                            call.respond(HttpStatusCode.Created, CreatedResponse(newId))

                        }

                    delete("/{id}") { //status OK
                        val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest,
                            ErrorResponse(400,"invalid id parameter","id must be an integer"))

                        val DeletedRow = transaction{
                            Participants
                                .deleteWhere { Participants.participantId eq id }
                        }

                        if(DeletedRow == 0){
                            call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"no participant found"))
                        }else{
                            call.respond(HttpStatusCode.NoContent)
                        }
                    }

                }
                    //jury 2/6
                route("/jury"){
                    get{ //status OK
                            val jury = transaction {
                                Jury
                                    .selectAll()
                                .map{it.toJuryResponseDTO()}
                            }
                        call.respond(jury)
                    }

                    post{ // status OK
                        val juryPost = call.receive<JuryRequestDTO>()

                            if(juryPost.firstName.isBlank()) throw ValidationException("First name is blank")
                            if(juryPost.lastName.isBlank()) throw ValidationException("Last name is blank")

                        val newId = transaction{
                            Jury.insert {
                                it[firstName] = juryPost.firstName
                                it[lastName] = juryPost.lastName
                            }get Jury.juryId
                        }

                        call.respond(HttpStatusCode.Created, CreatedResponse(newId))
                    }

                    delete("/{id}") { // status OK
                        val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest,
                            ErrorResponse(400,"invalid id parameter","id must be an integer"))

                        val DeletedRow = transaction{
                            Jury
                                .deleteWhere { Jury.juryId eq id }
                        }

                        if(DeletedRow == 0){
                            call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"no Jury found"))
                        }else{
                            call.respond(HttpStatusCode.NoContent)
                        }
                    }
                }
                    //categories 3/6
                route("/categories"){
                    get{ // status OK
                        val categories = transaction {
                            Categories
                            .selectAll()
                                .map{it.toCategoriesResponseDTO()}
                        }
                        call.respond(categories)
                    }

                    post{ // status OK
                       val categoriesBody = call.receive<CategoriesRequestDTO>()

                        val new = transaction{
                            Categories.insert {
                                it[name] = categoriesBody.name
                            }get Categories.categoryId
                        }
                        call.respond(HttpStatusCode.Created, CreatedResponse(new))
                    }

                    delete("/{id}"){ // status OK
                        val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest,
                            ErrorResponse(400,"invalid id parameter","id must be an integer"))

                        val DeletedRow = transaction{
                            Categories
                                .deleteWhere { Categories.categoryId eq id }
                        }

                        if(DeletedRow == 0){
                            call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"no category found"))
                        }else{
                            call.respond(HttpStatusCode.NoContent)
                        }
                    }
                }
                    //criteria 4/6
                route("/criteria"){
                    get{ // status OK
                        val criteria = transaction {
                            Criteria
                                .selectAll()
                                .map { it.toCriteriaResponseDTO() }
                        }
                        call.respond(criteria)
                    }

                    post{ // status OK
                        val criteriaBody = call.receive<CriteriaRequestDTO>()

                        if(criteriaBody.categoryId <= 0) throw ValidationException("Category id is invalid")
                        if(criteriaBody.name.isBlank()) throw ValidationException("First name is blank")
                        if(criteriaBody.maxPoints <= 0 ) throw ValidationException("Last name is blank")

                        val new = transaction{
                            Criteria
                            .insert {
                                it[categoryId] = criteriaBody.categoryId
                                it[name] = criteriaBody.name
                                it[maxPoints] = criteriaBody.maxPoints

                            }get Criteria.CriterionId
                        }
                        call.respond(HttpStatusCode.Created,CreatedResponse(new))
                    }

                    delete("/{id}"){ // status OK

                            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest,
                                ErrorResponse(400,"invalid id parameter","id must be an integer"))


                        val DeletedRow = transaction{
                            Criteria
                                .deleteWhere { Criteria.CriterionId eq id }
                        }
                            if(DeletedRow == 0){
                                call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"no category found"))
                            }else{
                                call.respond(HttpStatusCode.NoContent)
                            }

                    }
                }
            // schools 5/6

            route("/schools"){
                get { // status OK
                    val schools = transaction {
                        Schools
                            .selectAll()
                            .map{it.toSchoolsResponseDTO()}
                    }
                    call.respond(schools)
                }

                post{ // status OK
                    val SchoolBody = call.receive<SchoolRequestDTO>()

                    if(SchoolBody.name.isBlank()) throw ValidationException("name is blank")
                    if(SchoolBody.city.isBlank()) throw ValidationException("city is blank")

                    val ResultRow = transaction{
                        Schools.insert{
                            it[name] = SchoolBody.name
                            it[city] = SchoolBody.city
                        }get Schools.schoolId

                    }
                    call.respond(HttpStatusCode.Created, CreatedResponse(ResultRow))
                }

                delete("/{id}"){
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest,
                        ErrorResponse(400,"invalid id parameter","id must be an integer"))

                    val DeletedRow = transaction{
                        Schools
                            .deleteWhere { Schools.schoolId eq id }
                    }

                    if(DeletedRow == 0){
                        call.respond(HttpStatusCode.NotFound,ErrorResponse(404,"no category found"))
                    }else{
                        call.respond(HttpStatusCode.NoContent)
                    }
                }
            }

            //6/6



        }
    }



}
