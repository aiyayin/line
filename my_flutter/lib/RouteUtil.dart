import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_flutter/secendPage.dart';

class RouteUtil {
  static int routeCounter = 0;
//  static const platform = const MethodChannel('samples.flutter');

  static toSecondPage(
      BuildContext context, int _counter, callback(String result)) {
    Navigator.of(context)
        .push(new PageRouteBuilder(
            opaque: false,
            pageBuilder: (BuildContext context, _, __) {
              return new NewRoute(title: _counter);
            },
            transitionsBuilder:
                (_, Animation<double> animation, __, Widget child) {
              return new FadeTransition(
                opacity: animation,
                child: new FadeTransition(
                  opacity: new Tween<double>(begin: 0.5, end: 1.0)
                      .animate(animation),
                  child: child,
                ),
              );
            }))
        .then((wordPair) {
      callback(wordPair.toString());
      routeCounter++;
//      platform.setMethodCallHandler(platformCallHandler);
    });
  }

//static  Future<dynamic> platformCallHandler(MethodCall call) async {
//    switch (call.method) {
//      case "getRouteCounter":
//        return routeCounter;
//        break;
//    }
//  }
}
