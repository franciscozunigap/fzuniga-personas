package org.persona.com.repository.imp

import org.persona.com.model.Pais
import org.persona.com.model.PaisRequest
import org.persona.com.repository.PaisRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

class InMemoryPaisRepository : PaisRepository {
    private val paises = mutableListOf<Pais>()
    private val nextId = AtomicInteger(1)
    private val mutex = Mutex()

    init {
        paises.add(Pais(nextId.getAndIncrement(), "Metropolitana"))
    }
    override suspend fun getAll(): List<Pais> = mutex.withLock { paises.toList() }
    override suspend fun getById(id: Int): Pais? = mutex.withLock { paises.find { it.id == id } }
    override suspend fun getByRegion(region: String): Pais? = mutex.withLock { paises.find { it.region.equals(region, ignoreCase = true) } }
    override suspend fun add(request: PaisRequest): Pais = mutex.withLock {
        val nueva = Pais(nextId.getAndIncrement(), request.region)
        paises.add(nueva)
        nueva
    }
    override suspend fun update(id: Int, request: PaisRequest): Pais? = mutex.withLock { /* ... */ null }
    override suspend fun delete(id: Int): Boolean = mutex.withLock { paises.removeIf { it.id == id } }
}