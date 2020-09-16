package com.alvindizon.tampisaw.ui.details


data class PhotoDetails (
    val id: String,
    val createdAt: String?,
    val updatedAt: String?,
    val width: Int?,
    val height: Int?,
    val color: String? = "#E0E0E0",
    val views: String?,
    val downloads: String?,
    val likes: String?,
    val description: String?,
    val make: String?,
    val model: String?,
    val exposureTime: String?,
    val aperture: String?,
    val focalLength: String?,
    val iso: Int?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val download: String?,
    val downloadLocation: String?,
    val rawUrl: String,
    val fullUrl: String,
    val regularUrl: String,
    val smallUrl: String,
    val thumbUrl: String,
    val profileImageUrl: String?,
    val username: String?
)


val PhotoDetails.fileName: String
    get() = "${this.id}.jpg"