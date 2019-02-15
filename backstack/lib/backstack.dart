import 'dart:async';

import 'package:flutter/services.dart';

class Backstack {
  static const MethodChannel _channel =
      const MethodChannel('backstack');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
