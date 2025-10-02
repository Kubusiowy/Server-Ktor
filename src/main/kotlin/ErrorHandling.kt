package com.firek


import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

@Serializable
data class ErrorResponse(
    val code:Int,
    val message:String,
    val details:String? = null
)

@Serializable
data class GoodResponse(
    val code: Int,
    val message: String,
    val details: String? = null
)

class ValidationException(msg: String) : RuntimeException(msg)


fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<SerializationException>{call, _ ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(400,"Invalid JSON", "Body parse failed")
            )
        }

        exception<ValidationException> { call, e ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(400, "Validation error", e.message)
            )
        }


        status(HttpStatusCode.NotFound){call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(404, "Not found")
            )
        }

        exception<Throwable> { call, _ ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(500, "Internal Server Error")
            )

        }
    }

}