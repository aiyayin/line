import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:my_flutter/firstPage.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String defaultRouteName) {
  switch (defaultRouteName) {
    case 'route1':
      return MyApp();
    default:
      return Center(
        child: Text('Unknown route: $defaultRouteName',
            textDirection: TextDirection.ltr),
      );
  }
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}
