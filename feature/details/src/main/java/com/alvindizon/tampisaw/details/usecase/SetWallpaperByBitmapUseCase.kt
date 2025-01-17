package com.alvindizon.tampisaw.details.usecase

import android.net.Uri
import com.alvindizon.tampisaw.setwallpaper.WallpaperSettingManager
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SetWallpaperByBitmapUseCase @Inject constructor(
    private val wallpaperSettingManager: WallpaperSettingManager
) {

    fun setWallpaperByBitmap(uri: Uri): Completable {
        return Completable.create { emitter ->
            try {
                wallpaperSettingManager.setWallpaperByBitmap(uri)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }
    }
}
