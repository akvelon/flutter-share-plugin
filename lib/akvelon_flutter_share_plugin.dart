import 'dart:async';

import 'package:flutter/services.dart';

enum ShareType { IMAGE, VIDEO, PDF, FILE }

extension ShareTypeExtension on ShareType {
  String get method {
    switch (this) {
      case ShareType.IMAGE:
        return 'share_images';
      case ShareType.VIDEO:
        return 'share_videos';
      case ShareType.PDF:
        return 'share_pdfs';
      case ShareType.FILE:
        return 'share_files';
      default:
        return null;
    }
  }
}

class AkvelonFlutterSharePlugin {
  static const MethodChannel _channel =
  const MethodChannel('flutter_share_plugin');

  static Future<void> shareText(String text, {String title, String subject, String url}) {
    assert(text != null && text.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'text': text,
      'subject': subject,
      'chooser_title': title,
      'url': url
    };
    return _channel.invokeMethod('share_text', params);
  }

  static Future<void> shareSingle(String item, ShareType type,
      {String text, String subject}) {
    assert(item != null && item.isNotEmpty);
    List<String> items = [item];
    return _invokeShare(items, type, subject: subject, text: text);
  }

  static Future<void> shareMultiple(List<String> items, ShareType type,
      {String text, String subject}) {
    assert(items != null && items.isNotEmpty);
    return _invokeShare(items, type, subject: subject, text: text);
  }

  static Future<void> _invokeShare(List<String> items, ShareType type,
      {String text, String subject}) {
    assert(items != null && items.isNotEmpty);
    final Map<String, dynamic> params = <String, dynamic>{
      'items': items,
      'subject': subject,
      'text': text
    };
    return _channel.invokeMethod(type.method, params);
  }
}
