package com.morris.nasaimages.utils

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils {

    companion object {

        // Dates
        private val current = LocalDateTime.now()
        private val last = current.minusDays(15)

        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val endDate = current.format(formatter)
        private val startDate = last.format(formatter)

        fun getStartDate(): String = startDate

        fun getEndDate(): String = endDate

        fun getCurrentDate(): String = startDate

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



