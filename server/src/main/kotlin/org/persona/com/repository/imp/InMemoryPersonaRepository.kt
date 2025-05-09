package org.persona.com.repository.imp

import org.persona.com.model.Persona
import org.persona.com.model.PersonaRequest
import org.persona.com.repository.PersonaRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

class InMemoryPersonaRepository : PersonaRepository {
    private val Personas = mutableListOf<Persona>()
    private val nextId = AtomicInteger(1)
    private val mutex = Mutex()

    init {
        Personas.add(Persona(nextId.getAndIncrement(), "Francisco", 22))
    }

    override suspend fun getAll(): List<Persona> = mutex.withLock { Personas.toList() }

    override suspend fun getById(id: Int): Persona? = mutex.withLock {
        Personas.find { it.id == id }
    }

    override suspend fun add(request: PersonaRequest): Persona = mutex.withLock {
        val nuevoPersona = Persona(id = nextId.getAndIncrement(), nombre = request.nombre, edad = request.edad)
        Personas.add(nuevoPersona)
        nuevoPersona
    }

    override suspend fun update(id: Int, request: PersonaRequest): Persona? = mutex.withLock {
        val index = Personas.indexOfFirst { it.id == id }
        if (index != -1) {
            val PersonaActualizado = Personas[index].copy(nombre = request.nombre, edad = request.edad)
            Personas[index] = PersonaActualizado
            PersonaActualizado
        } else {
            null
        }
    }

    override suspend fun delete(id: Int): Boolean = mutex.withLock {
        Personas.removeIf { it.id == id }
    }

}