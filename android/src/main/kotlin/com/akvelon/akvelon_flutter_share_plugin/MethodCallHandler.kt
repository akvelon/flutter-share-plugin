package com.akvelon.akvelon_flutter_share_plugin

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.*


class MethodCallHandler(private val handler: ShareHandler): MethodChannel.MethodCallHandler {

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        require(call.arguments is Map<*, *>) { "Map argument expected" }
        try {
            when(call.method) {
                METHOD_SHARE_TEXT -> {
                    handler.shareText(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_CHOOSER_TITLE) as String?,
                            call.argument(KEY_URL) as String?
                    )
                    result.success(true)
                }
                METHOD_SHARE_IMAGE -> {
                    handler.shareImage(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILE) as String?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }
                METHOD_SHARE_IMAGES -> {
                    handler.shareImages(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILES) as? ArrayList<String>?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }

                METHOD_SHARE_VIDEO -> {
                    handler.shareVideo(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILE) as String?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }
                METHOD_SHARE_VIDEOS -> {
                    handler.shareVideos(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILES) as? ArrayList<String>?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }

                METHOD_SHARE_PDF -> {
                    handler.sharePdfs(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILES) as? ArrayList<String>?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }

                METHOD_SHARE_FILES -> {
                    handler.shareFiles(
                            call.argument(KEY_TEXT) as String?,
                            call.argument(KEY_SUBJECT) as String?,
                            call.argument(KEY_FILES) as? ArrayList<String>?,
                            call.argument(KEY_CHOOSER_TITLE) as String?
                    )
                    result.success(true)
                }


                else -> result.notImplemented()
            }
        } catch (exc: Exception) {
            result.error(exc.message, null, null)
        }
    }

    companion object {
        const val METHOD_SHARE_TEXT = "share_text"
        const val METHOD_SHARE_IMAGE = "share_image"
        const val METHOD_SHARE_IMAGES = "share_images"
        const val METHOD_SHARE_VIDEO = "share_video"
        const val METHOD_SHARE_VIDEOS = "share_videos"
        const val METHOD_SHARE_PDF = "share_pdf"
        const val METHOD_SHARE_FILES = "share_files"

        private const val KEY_TEXT = "text"
        private const val KEY_SUBJECT = "subject"
        private const val KEY_FILE = "filepath"
        private const val KEY_FILES = "items"
        private const val KEY_CHOOSER_TITLE = "chooser_title"
        private const val KEY_URL = "url"
    }
}