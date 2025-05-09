package org.persona.com.model


import kotlinx.serialization.Serializable

@Serializable
data class Pais(
    val id: Int,
    val region: String,
)

@Serializable
data class PaisRequest(
    val region: String,
)