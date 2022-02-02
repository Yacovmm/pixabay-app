package com.example.pixabayapp.data.models

data class ApiResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
) {

    data class Hit(
        val id: Int,
        val likes: String,
        val user: String,
        val imageURL: String,
        val previewURL: String,
        val fullHDURL: String
    )

}
