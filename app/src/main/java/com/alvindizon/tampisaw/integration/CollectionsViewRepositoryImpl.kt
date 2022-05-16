package com.alvindizon.tampisaw.integration

import androidx.paging.PagingData
import androidx.paging.map
import com.alvindizon.tampisaw.collections.integration.CollectionsViewRepository
import com.alvindizon.tampisaw.collections.model.Collection
import com.alvindizon.tampisaw.core.toCollection
import com.alvindizon.tampisaw.core.toPhoto
import com.alvindizon.tampisaw.domain.UnsplashRepo
import com.alvindizon.tampisaw.gallery.model.Photo
import io.reactivex.rxjava3.core.Observable

class CollectionsViewRepositoryImpl(private val unsplashRepo: UnsplashRepo) :
    CollectionsViewRepository {

    override fun getAllCollections(): Observable<PagingData<Collection>> {
        return unsplashRepo.getAllCollections().map { response ->
            response.map { it.toCollection() }
        }
    }

    override fun getCollectionPhotos(collectionId: String): Observable<PagingData<Photo>> {
        return unsplashRepo.getCollectionPhotos(collectionId).map { response ->
            response.map { it.toPhoto() }
        }
    }
}
