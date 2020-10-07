import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:akvelon_flutter_share_plugin/akvelon_flutter_share_plugin.dart';
import 'package:image_picker/image_picker.dart';
import 'package:multi_image_picker/multi_image_picker.dart';
import 'package:path_provider/path_provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final picker = ImagePicker();

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child: Center(
            child: Column(
              children: <Widget>[
                RaisedButton(
                  onPressed: () {
                    AkvelonFlutterSharePlugin.shareText("Here is how it works",
                        title: "share text title",
                        subject: "share text subject",
                        url: "https://pub.dev/");
                  },
                  child: Text("share text"),
                ),
                RaisedButton(
                  onPressed: () async {
                    PickedFile f =
                        await picker.getImage(source: ImageSource.gallery);
                    if (f != null) {
                      AkvelonFlutterSharePlugin.shareSingle(
                          f.path, ShareType.IMAGE,
                          text: "share image text",
                          subject: "share image subject");
                    }
                  },
                  child: Text("share image"),
                ),
                RaisedButton(
                  onPressed: () async {
                    PickedFile f =
                        await picker.getVideo(source: ImageSource.gallery);
                    if (f != null) {
                      AkvelonFlutterSharePlugin.shareSingle(
                        f.path,
                        ShareType.VIDEO,
                        text: "share video text",
                        subject: "share video subject",
                      );
                    }
                  },
                  child: Text("share video"),
                ),
                RaisedButton(
                  onPressed: () {
                    _shareStorageFile();
                  },
                  child: Text("share file"),
                ),
                RaisedButton(
                  onPressed: () {
                    _shareMultipleImages();
                  },
                  child: Text("share multiple images"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  ///share the storage file
  _shareStorageFile() async {
    Directory dir = Platform.isAndroid
        ? await getExternalStorageDirectory()
        : await getApplicationDocumentsDirectory();
    File testFile = File("${dir.path}/flutter/test.txt");
    if (!await testFile.exists()) {
      await testFile.create(recursive: true);
      testFile.writeAsStringSync("test for share documents file");
    }
    AkvelonFlutterSharePlugin.shareSingle(
      testFile.path,
      ShareType.FILE,
      text: "share file text",
      subject: "share file subject",
    );
  }

  ///share multiple images
  _shareMultipleImages() async {
    List<Asset> assetList = await MultiImagePicker.pickImages(maxImages: 5);
    var imageList = List<String>();
    for (var asset in assetList) {
      String path =
          await _writeByteToImageFile(await asset.getByteData(quality: 30));
      imageList.add(path);
    }
    AkvelonFlutterSharePlugin.shareMultiple(
      imageList,
      ShareType.IMAGE,
      text: "share multiple images text",
      subject: "share multiple images subject",
    );
  }

  ///write image byte data into file
  Future<String> _writeByteToImageFile(ByteData byteData) async {
    Directory dir = Platform.isAndroid
        ? await getExternalStorageDirectory()
        : await getApplicationDocumentsDirectory();
    File imageFile = new File(
        "${dir.path}/flutter/${DateTime.now().millisecondsSinceEpoch}.png");
    imageFile.createSync(recursive: true);
    imageFile.writeAsBytesSync(byteData.buffer.asUint8List(0));
    return imageFile.path;
  }
}
