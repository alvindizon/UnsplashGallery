package com.alvindizon.tampisaw.core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alvindizon.tampisaw.data.networking.model.getphoto.GetPhotoResponse
import com.alvindizon.tampisaw.data.networking.model.listphotos.ListPhotosResponse
import com.alvindizon.tampisaw.data.networking.model.listphotos.Urls
import com.alvindizon.tampisaw.data.networking.model.listphotos.User
import com.alvindizon.tampisaw.ui.details.PhotoDetails
import com.alvindizon.tampisaw.ui.gallery.UnsplashPhoto
import com.alvindizon.tampisaw.ui.gallery.UnsplashPhotoUrls
import com.alvindizon.tampisaw.ui.gallery.UnsplashUser
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

fun ListPhotosResponse.toUnsplashPhoto() = UnsplashPhoto(
    id, description, user.toUnsplashUser(), urls.toUnsplashUrls(), sponsorship != null, color,
    height, width
)

fun User.toUnsplashUser() = UnsplashUser(name, username, profileImage?.large)

fun Urls.toUnsplashUrls() = UnsplashPhotoUrls(raw, full, regular, small, thumb)

fun GetPhotoResponse.toPhotoDetails() = PhotoDetails(
    id, createdAt, updatedAt, "$width x $height", color, views.toCompactFormat(),
    downloads.toCompactFormat(), likes.toCompactFormat(), description,
    getCamera(), getLocationString(), links.download, links.downloadLocation,
    urls.raw, urls.full, urls.regular, urls.small, urls.thumb, user.profileImage?.large, user.name
)

fun Int.toCompactFormat(): String {
    if (this < 1000) return "$this"
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "KMGTPE"[exp - 1])
}

fun Context.hasWritePermission(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
            hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
}

fun Context.hasPermission(vararg permissions: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        permissions.all { singlePermission ->
            ContextCompat.checkSelfPermission(this, singlePermission) == PackageManager.PERMISSION_GRANTED
        }
    else true
}

fun Fragment.requestPermission(vararg permissions: String, @IntRange(from = 0) requestCode: Int) =
    requestPermissions(permissions, requestCode)

fun GetPhotoResponse.getLocationString(): String = when {
    location.city != null && location.country != null ->
        "${location.city}, ${location.country}"
    location.city != null && location.country == null -> location.city
    location.city == null && location.country != null -> location.country
    else -> "N/A"
}

fun GetPhotoResponse.getCamera(): String {
    return exif.run {
        model?.apply{
            val makeList = make?.split(" ")?.map { it.trim().toLowerCase(Locale.ROOT) }
            val modelList = model.split(" ").map { it.trim().toLowerCase(Locale.ROOT) }

            if(makeList?.intersect(modelList)?.isEmpty() == true) {
                "${makeList.first()} $model"
            } else model
        }
    } ?: "Unknown"
}
