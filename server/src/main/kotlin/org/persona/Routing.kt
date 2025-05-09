package org.persona

import org.persona.com.model.PersonaRequest
import org.persona.com.repository.PersonaRepository

import org.persona.com.model.PaisRequest
import org.persona.com.repository.PaisRepository

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    PersonaRepository: PersonaRepository,
    PaisRepository: PaisRepository
) {
    routing {
        route("/Personas") {
            get {
                call.respond(PersonaRepository.getAll())
            }

            post {
                try {
                    val PersonaRequest = call.receive<PersonaRequest>()
                    val PersonaCreado = PersonaRepository.add(PersonaRequest)
                    call.respond(HttpStatusCode.Created, PersonaCreado)
                } catch (e: ContentTransformationException) { // Error al deserializar JSON
                    call.respond(HttpStatusCode.BadRequest, "Cuerpo de la solicitud inválido: ${e.cause?.message ?: e.message}")
                } catch (e: Exception) { // Otros errores
                    call.respond(HttpStatusCode.InternalServerError, "Error inesperado: ${e.message}")
                }
            }

            route("/{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de Persona inválido.")
                        return@get
                    }
                    val Persona = PersonaRepository.getById(id)
                    Persona?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound, "Persona no encontrado.")
                }

                put {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de Persona inválido.")
                        return@put
                    }
                    try {
                        val PersonaRequest = call.receive<PersonaRequest>()

                        val PersonaActualizado = PersonaRepository.update(id, PersonaRequest)
                        PersonaActualizado?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound, "Persona no encontrado para actualizar.")
                    } catch (e: ContentTransformationException) {
                        call.respond(HttpStatusCode.BadRequest, "Cuerpo de la solicitud inválido: ${e.cause?.message ?: e.message}")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Error inesperado: ${e.message}")
                    }
                }

                delete {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de Persona inválido.")
                        return@delete
                    }
                    if (PersonaRepository.delete(id)) {
                        call.respond(HttpStatusCode.OK, "Persona eliminado.")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Persona no encontrado para eliminar.")
                    }
                }
            }

        }
        route("/Paises") {
            get {
                call.respond(PaisRepository.getAll())
            }

            post {
                try {
                    val paisRequest = call.receive<PaisRequest>()
                    val paisCreado = PaisRepository.add(paisRequest) // Aquí se pasa paisRequest
                    call.respond(HttpStatusCode.Created, paisCreado)
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest, "Cuerpo de la solicitud inválido: ${e.cause?.message ?: e.message}")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error inesperado: ${e.message}")
                }
            }

            route("/{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de pais inválido.")
                        return@get
                    }
                    val pais = PaisRepository.getById(id)
                    pais?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound, "pais no encontrado.")
                }

                put {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de pais inválido.")
                        return@put
                    }
                    try {
                        val paisRequest = call.receive<PaisRequest>()
                        val paisActualizado = PaisRepository.update(id, paisRequest)
                        paisActualizado?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound, "pais no encontrado para actualizar.")
                    } catch (e: ContentTransformationException) {
                        call.respond(HttpStatusCode.BadRequest, "Cuerpo de la solicitud inválido: ${e.cause?.message ?: e.message}")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Error inesperado: ${e.message}")
                    }
                }

                delete {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, "ID de pais inválido.")
                        return@delete
                    }
                    if (PaisRepository.delete(id)) {
                        call.respond(HttpStatusCode.OK, "Pais eliminado.")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Pais no encontrado para eliminar.")
                    }
                }
            }
        }
    }



}