import Flutter
import UIKit

public class SwiftAkvelonFlutterSharePlugin: NSObject, FlutterPlugin {
  let METHOD_SHARE_TEXT = "share_text"
      let METHOD_SHARE_IMAGES = "share_images"
      let METHOD_SHARE_VIDEOS = "share_videos"
      let METHOD_SHARE_PDF = "share_pdfs"
      let METHOD_SHARE_FILES = "share_files"

      let KEY_TEXT = "text"
      let KEY_SUBJECT = "subject"
      let KEY_FILES = "items"
      let KEY_URL = "url"
      let KEY_TITLE = "chooser_title"

      private var result: FlutterResult?

      public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_share_plugin", binaryMessenger: registrar.messenger())
        let instance = SwiftAkvelonFlutterSharePlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
      }

      public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if (METHOD_SHARE_TEXT == call.method) {
            self.result = result
            result(shareText(call: call))
        } else if (METHOD_SHARE_FILES == call.method) {
            self.result = result
            result(shareFile(call: call))
        } else if (METHOD_SHARE_IMAGES == call.method) {
            self.result = result
            result(shareImage(call: call))
        } else if (METHOD_SHARE_VIDEOS == call.method) {
            self.result = result
            result(shareFile(call: call))
        } else if (METHOD_SHARE_PDF == call.method) {
            self.result = result
            result(shareFile(call: call))
        } else if (METHOD_SHARE_FILES == call.method) {
            self.result = result
            result(shareFile(call: call))
        } else {
            result(FlutterMethodNotImplemented)
        }
      }

      public func shareText(call: FlutterMethodCall) -> Bool {
          let args = call.arguments as? [String: Any?]

          let title = args![KEY_SUBJECT] as? String
          let text = args![KEY_TEXT] as? String
          let url = args![KEY_URL] as? String

          if (title == nil || title!.isEmpty) {
              return false
          }

          var sharedItems : Array<NSObject> = Array()
          var textList : Array<String> = Array()

          if (text != nil && text != "") {
              textList.append(text!)
          }

          if (url != nil && url != "") {
              textList.append(url!)
          }

          var textToShare = ""

          if (!textList.isEmpty) {
              textToShare = textList.joined(separator: "\n\n")
          }

          sharedItems.append((textToShare as NSObject?)!)

          let activityViewController = UIActivityViewController(activityItems: sharedItems, applicationActivities: nil)

          if (title != nil && title != "") {
              activityViewController.setValue(title, forKeyPath: KEY_SUBJECT);
          }

          DispatchQueue.main.async {
              UIApplication.topViewController()?.present(activityViewController, animated: true, completion: nil)
          }

          return true
      }

      public func shareFile(call: FlutterMethodCall) -> Bool {
          let args = call.arguments as? [String: Any?]

          let title = args![KEY_SUBJECT] as? String
          let text = args![KEY_TEXT] as? String
          let files = args![KEY_FILES] as? Array<String>

          if (title == nil || title!.isEmpty || files == nil || files!.isEmpty) {
              return false
          }

          var sharedItems : Array<NSObject> = Array()

          // text
          if (text != nil && text != "") {
              sharedItems.append((text as NSObject?)!)
          }

          // File url
          if (files != nil && !files!.isEmpty) {
              for file in files! {
                  let filePath = URL(fileURLWithPath: file)
                  sharedItems.append(filePath as NSObject)
              }
          }

          let activityViewController = UIActivityViewController(activityItems: sharedItems, applicationActivities: nil)

          if (title != nil && title != "") {
              activityViewController.setValue(title, forKeyPath: KEY_SUBJECT);
          }

          if UIDevice.current.userInterfaceIdiom == .pad {
            activityViewController.popoverPresentationController?.sourceView = UIApplication.topViewController()?.view
            if let view = UIApplication.topViewController()?.view {
                activityViewController.popoverPresentationController?.permittedArrowDirections = []
                activityViewController.popoverPresentationController?.sourceRect = CGRect(x: view.bounds.midX, y: view.bounds.midY, width: 0, height: 0)
            }
          }

          UIApplication.topViewController()?.present(activityViewController, animated: true, completion: nil)

          return true
      }

      public func shareImage(call: FlutterMethodCall) -> Bool {
          let args = call.arguments as? [String: Any?]

          let title = args![KEY_SUBJECT] as? String
          let text = args![KEY_TEXT] as? String
          let files = args![KEY_FILES] as? Array<String>

          if (title == nil || title!.isEmpty || files == nil || files!.isEmpty) {
              return false
          }

          var sharedItems : Array<NSObject> = Array()

          // text
          if (text != nil && text != "") {
              sharedItems.append((text as NSObject?)!)
          }

          // File url
          if (files != nil && !files!.isEmpty) {
              for file in files! {
                  let filePath = UIImage(contentsOfFile: file)
                  sharedItems.append(filePath!)
              }
          }

          let activityViewController = UIActivityViewController(activityItems: sharedItems, applicationActivities: nil)

          if (title != nil && title != "") {
              activityViewController.setValue(title, forKeyPath: KEY_SUBJECT);
          }

          if UIDevice.current.userInterfaceIdiom == .pad {
            activityViewController.popoverPresentationController?.sourceView = UIApplication.topViewController()?.view
            if let view = UIApplication.topViewController()?.view {
                activityViewController.popoverPresentationController?.permittedArrowDirections = []
                activityViewController.popoverPresentationController?.sourceRect = CGRect(x: view.bounds.midX, y: view.bounds.midY, width: 0, height: 0)
            }
          }

          UIApplication.topViewController()?.present(activityViewController, animated: true, completion: nil)

          return true
      }

  }

  extension UIApplication {
      class func topViewController(controller: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
          if let navigationController = controller as? UINavigationController {
              return topViewController(controller: navigationController.visibleViewController)
          }
          if let tabController = controller as? UITabBarController {
              if let selected = tabController.selectedViewController {
                  return topViewController(controller: selected)
              }
          }
          if let presented = controller?.presentedViewController {
              return topViewController(controller: presented)
          }
          return controller
      }
}
