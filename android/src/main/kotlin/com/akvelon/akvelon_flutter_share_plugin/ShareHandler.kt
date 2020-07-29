package com.akvelon.akvelon_flutter_share_plugin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.lang.IllegalStateException


class ShareHandler(activity: Activity?) {

    private var activity: Activity? = null

    init {
        this.activity = activity
    }

    fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    fun shareText(text: String?, subject: String?, chooserTitle: String?, url: String?) {
        require(!(text == null || text.isEmpty())) { "Non-empty text expected" }
        val shareText = if(url != null) "$text \n $url" else text
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.type = "text/plain"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun shareImage(text: String?, subject: String?, filePath: String?, chooserTitle: String?) {
        require(!(filePath == null || filePath.isEmpty())) { "Non-empty filePath expected" }
        val file = File(filePath)
        val fileUri = FileProvider.getUriForFile(activity!!,activity!!.applicationContext.packageName + ".provider", file)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "image/*"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun sharePdfs(text: String?, subject: String?, filePaths: ArrayList<String>?, chooserTitle: String?) {
        require(!(filePaths == null || filePaths.isEmpty())) { "Non-empty filePaths expected" }
        val fileUris = ArrayList(filePaths.map { getFileUri(File(it)) })

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "application/pdf"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun shareImages(text: String?, subject: String?, filePaths: ArrayList<String>?, chooserTitle: String?) {
        require(!(filePaths == null || filePaths.isEmpty())) { "Non-empty filePaths expected" }
        val fileUris = ArrayList(filePaths.map { getFileUri(File(it)) })

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "video/*"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun shareVideo(text: String?, subject: String?, filePath: String?, chooserTitle: String?) {
        require(!(filePath == null || filePath.isEmpty())) { "Non-empty filePath expected" }
        val file = File(filePath)
        val fileUri = FileProvider.getUriForFile(activity!!,activity!!.applicationContext.packageName + ".provider", file)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "video/*"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun shareVideos(text: String?, subject: String?, filePaths: ArrayList<String>?, chooserTitle: String?) {
        require(!(filePaths == null || filePaths.isEmpty())) { "Non-empty filePaths expected" }
        val fileUris = ArrayList(filePaths.map { getFileUri(File(it)) })

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "image/*"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }

    fun shareFiles(text: String?, subject: String?, filePaths: ArrayList<String>?, chooserTitle: String?) {
        require(!(filePaths == null || filePaths.isEmpty())) { "Non-empty filePaths expected" }
        val fileUris = ArrayList(filePaths.map { getFileUri(File(it)) })

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "*/*"
        val chooserIntent = Intent.createChooser(shareIntent, chooserTitle)
        start(chooserIntent)
    }


    private fun getFileUri(file: File): Uri {
         return FileProvider.getUriForFile(activity!!,activity!!.applicationContext.packageName + ".provider", file)
    }

    private fun start(intent: Intent) {
        if(activity != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity!!.startActivity(intent)
        } else throw IllegalStateException("Activity is null")
    }
}