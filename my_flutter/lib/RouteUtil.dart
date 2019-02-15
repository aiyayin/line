import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_flutter/secendPage.dart';

class RouteUtil {
  static int routeCounter = 0;

//  static const platform = const MethodChannel('samples.flutter');

  static toSecondPage(
      BuildContext context, int _counter, callback(String result)) {
    routeCounter++;
    _setStackCounter();
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
    });
  }

//获取到插件与原生的交互通道
  static const jumpPlugin = const MethodChannel('line/plugin/backstack');

  static Future<Null> _setStackCounter() async {
    Map<String, String> map = {"flutter": routeCounter.toString()};

    String result = await jumpPlugin.invokeMethod('twoAct', map);

    print(result);
  }
}
