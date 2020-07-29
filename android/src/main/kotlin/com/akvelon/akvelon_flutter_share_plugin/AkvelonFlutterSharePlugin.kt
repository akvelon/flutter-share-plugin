package com.akvelon.akvelon_flutter_share_plugin

import android.app.Activity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.PluginRegistry.Registrar

/** AkvelonFlutterSharePlugin */
class AkvelonFlutterSharePlugin: FlutterPlugin, ActivityAware {
  private val CHANNEL = "flutter_share_plugin"
  private var handler: MethodCallHandler? = null
  private var shareHandler: ShareHandler? = null
  private var methodChannel: MethodChannel? = null

  fun registerWith(registrar: Registrar) {
    val plugin = AkvelonFlutterSharePlugin()
    plugin.setUpChannel(registrar.activity(), registrar.messenger())
  }

  override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    setUpChannel(null, binding.binaryMessenger)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    methodChannel!!.setMethodCallHandler(null)
    methodChannel = null
    shareHandler = null
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    shareHandler?.setActivity(binding.activity)
  }

  override fun onDetachedFromActivity() {
    tearDownChannel()
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity()
  }

  private fun setUpChannel(activity: Activity?, messenger: BinaryMessenger) {
    methodChannel = MethodChannel(messenger, CHANNEL)
    shareHandler = ShareHandler(activity)
    handler = MethodCallHandler(shareHandler!!)
    methodChannel!!.setMethodCallHandler(handler)
  }

  private fun tearDownChannel() {
    shareHandler?.setActivity(null)
    methodChannel!!.setMethodCallHandler(null)
  }
}
