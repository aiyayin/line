import 'package:flutter/material.dart';
import 'package:my_flutter/RouteUtil.dart';

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String callbackRandomString;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  void _decreaseCounter() {
    setState(() {
      _counter--;
    });
  }

  void _newRouteCallback(String callbackRandom) {
    setState(() {
      callbackRandomString = callbackRandom;
    });
  }

  @override
  Widget build(BuildContext context) {
    print('ying>>> flutter build ');
    return Scaffold(
//      appBar: AppBar(
//         title: Text(widget.title),
//      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.display1,
            ),
            FloatingActionButton(
              heroTag: "btnDecrease",
              onPressed: _decreaseCounter,
              tooltip: 'Decrease',
              child: Icon(Icons.minimize),
            ),
            FlatButton(
              child: Text("open new route    $callbackRandomString"),
              onPressed: () {
                RouteUtil.toSecondPage(context,_counter,(result){
                  _newRouteCallback(result);
                });
              },
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        heroTag: "btnIncrease",
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
