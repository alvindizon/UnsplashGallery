package com.alvindizon.tampisaw.domain

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.alvindizon.tampisaw.TestConstants
import com.alvindizon.tampisaw.data.file.FileManager
import com.alvindizon.tampisaw.data.wallpaper.WallpaperSettingManager
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DownloadPhotoUseCaseTest {

    private val wallpaperSettingManager: WallpaperSettingManager = mockk()

    private val activity: Activity = mockk()

    private val lifecycleOwner: LifecycleOwner = mockk()

    private val uri: Uri = mockk()

    private val fileManager: FileManager = mockk()

    private lateinit var useCase: DownloadPhotoUseCase

    @BeforeEach
    fun setUp() {
        useCase = DownloadPhotoUseCase(wallpaperSettingManager, fileManager)
    }

    @Test
    fun `verify usecase has no errors when photo is successfully downloaded`() {
        every { fileManager.getUriForPhoto(any(), any()) } returns Single.just(uri)
        every {
            wallpaperSettingManager.downloadPhoto(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns Completable.complete()

        useCase.downloadPhoto(
            TestConstants.QUALITY,
            TestConstants.FILENAME,
            TestConstants.ID,
            activity,
            lifecycleOwner
        ).test().apply {
            assertNoErrors()
            assertValueAt(0) { it == uri }
        }
    }
}