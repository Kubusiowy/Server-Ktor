package com.firek

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.firek.database.ParticipantDTO
import com.firek.database.Participants
import com.firek.database.toParticipantDTO
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
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {

            get("/participants"){
                val list = transaction {
                    Participants.selectAll().map{it.toParticipantDTO()}
                }
                call.respond(list)
            }

            get("/participants/{id}") {

                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest,
                    ErrorResponse(400, "Missing or malformed id"))

                val dto: ParticipantDTO = transaction {
                    Participants
                        .select(Participants.participantId eq id)
                        .singleOrNull()
                        ?.toParticipantDTO()

                }?: return@get call.respond(HttpStatusCode.NotFound, ErrorResponse(404, "Not found"))

               call.respond(dto)


            }

    }
}
