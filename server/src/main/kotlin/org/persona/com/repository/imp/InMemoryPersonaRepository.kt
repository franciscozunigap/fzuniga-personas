package org.persona.com.repository.imp

import org.persona.com.model.Persona
import org.persona.com.model.PersonaRequest
import org.persona.com.repository.PersonaRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

class InMemoryPersonaRepository : PersonaRepository {
    private val Personaes = mutableListOf<Persona>()
    private val nextId = AtomicInteger(1)
    private val mutex = Mutex()

    init {
        Personaes.add(Persona(nextId.getAndIncrement(), "Francisco", 22)) // Asumiendo especieId 1 es Perro
    }

    override suspend fun getAll(): List<Persona> = mutex.withLock { Personaes.toList() }

    override suspend fun getById(id: Int): Persona? = mutex.withLock {
        Personaes.find { it.id == id }
    }

    override suspend fun add(request: PersonaRequest): Persona = mutex.withLock {
        val nuevoPersona = Persona(id = nextId.getAndIncrement(), nombre = request.nombre, edad = request.edad)
        Personaes.add(nuevoPersona)
        nuevoPersona
    }

    override suspend fun update(id: Int, request: PersonaRequest): Persona? = mutex.withLock {
        val index = Personaes.indexOfFirst { it.id == id }
        if (index != -1) {
            val PersonaActualizado = Personaes[index].copy(nombre = request.nombre, edad = request.edad)
            Personaes[index] = PersonaActualizado
            PersonaActualizado
        } else {
            null
        }
    }

    override suspend fun delete(id: Int): Boolean = mutex.withLock {
        Personaes.removeIf { it.id == id }
    }

}