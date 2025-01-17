package com.alvindizon.tampisaw.details.usecase

import android.app.Activity
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LifecycleOwner
import com.alvindizon.tampisaw.setwallpaper.WallpaperSettingManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.rxjava3.core.Completable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DownloadPhotoUseCaseTest {

    private val wallpaperSettingManager: WallpaperSettingManager = mockk()

    private val activity: Activity = mockk()

    private val lifecycleOwner: LifecycleOwner = mockk()

    private val uri: Uri = mockk()

    private lateinit var useCase: DownloadPhotoUseCase

    @BeforeEach
    fun setUp() {
        useCase = DownloadPhotoUseCase(wallpaperSettingManager)
    }

    @Test
    fun `verify usecase has no errors when photo is successfully downloaded`() {
        // TODO getExternalStoragePublicDirectory in android.os.Environment not mocked.
//        every {
//            wallpaperSettingManager.downloadPhoto(
//                any(),
//                any(),
//                any(),
//                any(),
//                any()
//            )
//        } returns Completable.complete()
//
//        useCase.downloadPhoto(QUALITY, FILENAME, ID, activity, lifecycleOwner).test().apply {
//            assertNoErrors()
//            assertValueAt(0) { it == uri }
//        }
    }

    companion object {
        const val QUALITY = "full"
        const val FILENAME = "ASGAASGAS"
        const val ID = "id"
    }
}
