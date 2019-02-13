import 'dart:math' as math;
import 'dart:ui';

import 'package:flutter/material.dart';
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
    final int wordPair = new math.Random().nextInt(100);
    Center content = new Center(
      heightFactor: 3,
      child: Text.rich(TextSpan(children: [
        TextSpan(
          text: "是的了，这是第二个页面了！\n\n",
          style: new TextStyle(fontSize: 18),
        ),
        TextSpan(
            text: "传过来的参数 : ",
            style: new TextStyle(
                fontSize: 18,
                fontFamily: 'pingfang',
                fontWeight: FontWeight.w900)),
        TextSpan(
            text: widget.title.toString(),
            style: new TextStyle(
                color: Colors.amber,
                fontSize: 18,
                fontFamily: 'pingfang',
                fontWeight: FontWeight.w900)),
        TextSpan(
            text: "\n\n随机数 ： " + wordPair.toString(),
            style: new TextStyle(
                color: Colors.blue,
                fontSize: 18,
                fontFamily: 'pingfang',
                fontWeight: FontWeight.w900)),
      ])),
    );

    return new WillPopScope(
        child: new Scaffold(
            backgroundColor: Colors.white,
            appBar: AppBar(
              title: Text("new Route"),
            ),
            body: Padding(
                padding: new EdgeInsets.all(8),
                child: DecoratedBox(
                    decoration: BoxDecoration(
                        gradient: LinearGradient(
                            colors: [Colors.green[100], Colors.green[400]]),
                        border: Border.all(color: Colors.blue),
                        borderRadius: BorderRadius.all(new Radius.circular(5))),
                    child: Transform.rotate(
                      angle: math.pi / 2,
                      child: content,
                    )))),
        onWillPop: () {
          Navigator.of(context).pop(wordPair);
          RouteUtil.routeCounter--;
        });
  }
}
