package com.example.galleryapp.core.services.models

data class PhotoResponse(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val current_user_collections: List<CurrentUserCollection>,
    val description: String,
    val alt_description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int,
    val views: Int,
    val downloads: Int
)