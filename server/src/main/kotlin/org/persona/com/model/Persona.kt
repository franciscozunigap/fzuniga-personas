package org.persona.com.model

import kotlinx.serialization.Serializable

@Serializable
data class Persona(
    val id: Int,
    val nombre: String,
    val edad: Int,
)

@Serializable
data class PersonaRequest(
    val nombre: String,
    val edad: Int,
)