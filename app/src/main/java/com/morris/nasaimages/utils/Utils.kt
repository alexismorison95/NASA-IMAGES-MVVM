package com.morris.nasaimages.utils

import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils {

    companion object {

        // Dates
        private val current = LocalDateTime.now()
        private val last = current.minusDays(15)

        private fun formatter(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

        fun getStartDate(): String {

            val form = formatter("yyyy-MM-dd")

            return last.format(form)
        }

        fun getEndDate(): String {

            val form = formatter("yyyy-MM-dd")

            return current.format(form)
        }

        fun getCurrentDate(): String {

            val currentToSave = LocalDateTime.now()

            val form = formatter("yyyy-MM-dd HH:mm:ss")

            return currentToSave.format(form)
        }

        // Share url of image
        fun shareItem(context: Context, url: String) {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share URL")
            context.startActivity(shareIntent)
        }

        // Show messages
        fun showSnackbar(contextView: View, msg: String) {

            Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG).show()
        }
    }
}



