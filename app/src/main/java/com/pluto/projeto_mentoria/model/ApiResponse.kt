package com.pluto.projeto_mentoria.model

data class ApiResponse (
    val page: Int,
    val results: List<Movie>
)