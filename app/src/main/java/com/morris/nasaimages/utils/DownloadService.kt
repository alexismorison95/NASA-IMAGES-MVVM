package com.morris.nasaimages.utils

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import com.morris.nasaimages.R
import java.io.File

class DownloadService {

    // En el futuro seria bueno actualizar este servicio para funcionar con coroutines..

    companion object {

        private var status: Int? = null

        fun downloadImage(context: Context, url: String, flag: Int) {

            Permission.askPermissions(context)

            val directory = File(Environment.DIRECTORY_PICTURES)

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val downloadManager = getSystemService(context, DownloadManager::class.java) as DownloadManager

            val downloadUri = Uri.parse(url)

            val request = DownloadManager.Request(downloadUri).apply {

                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(url.substring(url.lastIndexOf("/") + 1))
                    .setDescription("")
                    .setDestinationInExternalPublicDir(
                        directory.toString(),
                        url.substring(url.lastIndexOf("/") + 1)
                    )
            }

            val downloadId = downloadManager.enqueue(request)
            val query = DownloadManager.Query().setFilterById(downloadId)

            Thread {

                var downloading = true

                while (downloading) {

                    val cursor: Cursor = downloadManager.query(query)
                    cursor.moveToFirst()

                    val cursorStatus =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                    if (cursorStatus == DownloadManager.STATUS_SUCCESSFUL || cursorStatus == DownloadManager.STATUS_FAILED) {

                        downloading = false
                    }

                    status = cursorStatus

                    cursor.close()
                }

                when (status) {
                    DownloadManager.STATUS_FAILED -> {

                        NoficationService.sendNotification(
                            context,
                            NoficationService.DOWN_IMAGE_FAILED,
                            NoficationService.TRY_AGAIN,
                            R.drawable.ic_baseline_favorite_24
                        )
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {

                        if (flag != WallpaperService.NOT_SET_WALLPAPER) {

                            WallpaperService.setWallpaper(context, directory, url, flag)
                        } else {
                            NoficationService.sendNotification(
                                context,
                                NoficationService.DOWN_IMAGE_SUCCESS,
                                "The image is in the folder $directory",
                                R.drawable.ic_baseline_favorite_24
                            )
                        }
                    }
                }
            }.start()
        }
    }
}