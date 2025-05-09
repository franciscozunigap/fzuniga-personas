package org.persona.com.repository

import org.persona.com.model.Pais
import org.persona.com.model.PaisRequest

interface PaisRepository {
    suspend fun getAll(): List<Pais>
    suspend fun getById(id: Int): Pais?
    suspend fun getByRegion(region: String): Pais?
    suspend fun add(request: PaisRequest): Pais
    suspend fun update(id: Int, request: PaisRequest): Pais?
    suspend fun delete(id: Int): Boolean
}