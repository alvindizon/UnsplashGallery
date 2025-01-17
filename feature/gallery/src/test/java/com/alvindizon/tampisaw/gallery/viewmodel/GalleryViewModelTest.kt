package com.alvindizon.tampisaw.gallery.viewmodel

import com.alvindizon.tampisaw.gallery.TestConstants
import com.alvindizon.tampisaw.gallery.usecase.GetAllPhotosUseCase
import com.alvindizon.tampisaw.testbase.CoroutineExtension
import com.alvindizon.tampisaw.testbase.InstantExecutorExtension
import com.alvindizon.tampisaw.testbase.RxSchedulerExtension
import com.alvindizon.tampisaw.testbase.collectData
import com.alvindizon.tampisaw.testbase.testObserver
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException


@ExtendWith(value = [InstantExecutorExtension::class, RxSchedulerExtension::class, CoroutineExtension::class])
class GalleryViewModelTest {

    @MockK
    lateinit var getAllPhotosUseCase: GetAllPhotosUseCase

    private lateinit var viewModel: GalleryViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = GalleryViewModel(getAllPhotosUseCase)
    }

    @Test
    fun `getAllPhotos loads correct PagingData of type UnsplashPhoto`() {
        val uiState = viewModel.uiState.testObserver()

        every { getAllPhotosUseCase.getAllPhotos() } returns Observable.just(TestConstants.getAllPhotosPagingData)

        viewModel.getAllPhotos()

        runBlocking {
            val photoList = uiState.observedValues[0]?.collectData()
            assertEquals(TestConstants.photo1, photoList?.get(0))
            assertEquals(TestConstants.photo2, photoList?.get(1))
        }
    }

    @Test
    fun `empty paging data if error is encountered`() {
        val uiState = viewModel.uiState.testObserver()

        every { getAllPhotosUseCase.getAllPhotos() } returns Observable.error(IOException())

        viewModel.getAllPhotos()

        uiState.observedValues.isEmpty().let { assert(it) }
    }
}
