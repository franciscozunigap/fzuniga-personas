package org.persona.com.repository


import org.persona.com.model.Persona
import org.persona.com.model.PersonaRequest

interface PersonaRepository {
    suspend fun getAll(): List<Persona>
    suspend fun getById(id: Int): Persona?
    suspend fun add(request: PersonaRequest): Persona
    suspend fun update(id: Int, request: PersonaRequest): Persona?
    suspend fun delete(id: Int): Boolean
}