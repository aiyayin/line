import 'package:flutter/material.dart';
import 'dart:ui';

import 'package:my_flutter/RouteUtil.dart';

class NewRoute extends StatefulWidget {
  final int title;

  NewRoute({this.title});

  @override
  State<StatefulWidget> createState() {
    _NewRoutePageState view = _NewRoutePageState();
    return view;
  }
}

class _NewRoutePageState extends State<NewRoute> {
  @override
  Widget build(BuildContext context) {
    print("ying>>>new Route");
    return new WillPopScope(
        child: new Scaffold(
          backgroundColor: Colors.white,
          appBar: AppBar(
            title: Text("new Route"),
          ),
          body: Center(
            child: Text(
              "是的，第二个页面了\n传过来的参数 " +
                  widget.title.toString() +
                  "  随机数 ： ",
              style: new TextStyle(color: Colors.amber, fontSize: 18),
            ),
          ),
        ),
        onWillPop: () {
          Navigator.of(context).pop(12);
          RouteUtil.routeCounter--;
        });
  }
}
