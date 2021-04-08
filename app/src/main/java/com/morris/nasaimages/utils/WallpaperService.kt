package com.morris.nasaimages.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.morris.nasaimages.R
import java.io.File
import java.io.FileNotFoundException

class WallpaperService {

    companion object {

        const val NOT_SET_WALLPAPER = -1
        private const val MAIN_SCREEN = 0
        private const val LOCK_SCREEN = 1
        private const val BOTH_SCREEN = 2

        // options = 0, 1, 2
        private val options = arrayOf("Main screen", "Lock screen", "Main and lock screen")

        fun selectDialogWallpaper(context: Context, view: View, url: String) {

            val builder = MaterialAlertDialogBuilder(context)

            with(builder) {

                this.setTitle("Set wallpaper as")
                setItems(options) { _, which ->

                    Utils.showSnackbar(view, "Setting wallpaper, progress is shown in notifications")

                    DownloadService.downloadImage(context, url, which)
                }
                show()
            }

        }

        fun setWallpaper(context: Context, directory: File, url: String, flag: Int) {

            val wallManager = WallpaperManager.getInstance(context)

            try {
                val sdcardPath = getExternalStorageDirectory().toString()

                val filePath = sdcardPath +
                        File.separator +
                        "$directory" +
                        File.separator +
                        url.substring(url.lastIndexOf("/") + 1)

                Log.d("filepath", "downloadImage: $filePath")

                val bitmap = BitmapFactory.decodeFile(filePath)

                when (flag) {

                    MAIN_SCREEN -> {
                        wallManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                    }
                    LOCK_SCREEN -> {
                        wallManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK
                        )
                    }
                    BOTH_SCREEN -> wallManager.setBitmap(bitmap)
                }

                NoficationService.sendNotification(
                    context,
                    NoficationService.SET_WALLP_SUCCESS,
                    NoficationService.ENJOY_WALLP,
                    R.drawable.ic_baseline_file_download_done_24
                )

                try {
                    val contentResolver = context.contentResolver

                    contentResolver.delete(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.Media.DATA + "=?",
                        arrayOf(filePath))
                }
                catch (e: Exception) {

                    Log.d("Wallpaper service", "contentResolver: ${e.message}")
                }
            }
            catch (e: Exception) {

                setWallpaper(context, directory, url, flag)
            }
        }
    }
}