# Flutter Share Plugin

A Flutter plugin for both iOS and Android that provides ability to share plain text, links and local files. Multiple file share is also supported.

# Screenshots

<details>
    <summary>Click to see how it works</summary>
<img src="https://raw.githubusercontent.com/akvelon/flutter-share-plugin/master/art/ios.gif" height="522" width="263"/>

<img
src="https://raw.githubusercontent.com/akvelon/flutter-share-plugin/master/art/android.gif" height="522" width="263"/>
</details>

## Installation

In the beginning, please add `flutter_share_plugin` as a [dependency in your pubspec.yaml file](https://flutter.io/using-packages/).

### iOS

Make sure that this row exists in your  `ios/podfile` file after target runner:

```
...

target 'Runner' do
    use_frameworks!

...
```

### Android

Please note that if you want to share files, you need to setup a  `FileProvider` which will give access to the files for sharing with other applications.

Add to `AndroidManifest.xml`:

```
<application>
...
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_provider_paths"/>
</provider>
</application>
...
```

Add `res/xml/file_provider_paths.xml`:

```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <cache-path name="cache" path="."/>
    <files-path name="cache" path="."/>
</paths>
```
For full document about `FileProvider` please refer to the [Android documentation](https://developer.android.com/reference/android/support/v4/content/FileProvider).

## Usage

Here is an example of flutter app sharing text, images and files:

```dart
...

            RaisedButton(
                onPressed: () {
                  FlutterSharePlugin.shareText("Here is how it works",
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
                    FlutterSharePlugin.shareSingle(f.path, ShareType.IMAGE,
                        title: "share image title",
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
                    FlutterSharePlugin.shareSingle(
                      f.path,
                      ShareType.VIDEO,
                      title: "share video title",
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
    FlutterSharePlugin.shareSingle(
      testFile.path,
      ShareType.FILE,
      title: "share file title",
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
    FlutterSharePlugin.shareMultiple(
      imageList,
      ShareType.IMAGE,
      title: "share multiple images title",
      subject: "share multiple images subject",
    );
  }

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
```

If you have any troubles, please, create an [issue](https://github.com/akvelon/flutter-share-plugin/issues)
