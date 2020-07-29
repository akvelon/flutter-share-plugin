#import "AkvelonFlutterSharePlugin.h"
#if __has_include(<akvelon_flutter_share_plugin/akvelon_flutter_share_plugin-Swift.h>)
#import <akvelon_flutter_share_plugin/akvelon_flutter_share_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "akvelon_flutter_share_plugin-Swift.h"
#endif

@implementation AkvelonFlutterSharePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAkvelonFlutterSharePlugin registerWithRegistrar:registrar];
}
@end
