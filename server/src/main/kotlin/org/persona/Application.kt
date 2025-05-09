package org.persona
import org.persona.configureRouting
import org.persona.configureSerialization

import org.persona.com.repository.imp.InMemoryPersonaRepository
import org.persona.com.repository.imp.InMemoryPaisRepository
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val PersonaRepository = InMemoryPersonaRepository()
    val PaisRepository = InMemoryPaisRepository()

    configureSerialization()
    configureRouting(PersonaRepository, PaisRepository)
}