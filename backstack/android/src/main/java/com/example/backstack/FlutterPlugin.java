//package com.example.backstack;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.util.Log;
//
//import io.flutter.plugin.common.BinaryMessenger;
//import io.flutter.plugin.common.EventChannel;
//import io.flutter.plugin.common.PluginRegistry;
//
///**
// * Created by ying.fu.
// * Date: 2019/2/15.
// */
//public class FlutterPlugin implements EventChannel.StreamHandler {
//
//    public static String CHANNEL = "line/plugin/backstack";
//
//    static EventChannel channel;
//
//    private Activity activity;
//
//    private FlutterPlugin(Activity activity) {
//        this.activity = activity;
//    }
//
//    public static void registerWith(PluginRegistry.Registrar registrar) {
//        channel = new EventChannel(registrar.messenger(), CHANNEL);
//        FlutterPlugin instance = new FlutterPlugin(registrar.activity());
//        channel.setStreamHandler(instance);
//    }
//
//  public static void registerEventChannel(BinaryMessenger flutterView, final Activity activity) {
//      new EventChannel(flutterView, "samples.flutter.io/charging").setStreamHandler(
//              new EventChannel.StreamHandler() {
//                  // 接收电池广播的BroadcastReceiver。
//                  private BroadcastReceiver chargingStateChangeReceiver;
//                  @Override
//                  // 这个onListen是Flutter端开始监听这个channel时的回调，第二个参数 EventSink是用来传数据的载体。
//                  public void onListen(Object arguments, EventChannel.EventSink events) {
//                      chargingStateChangeReceiver = createChargingStateChangeReceiver(events);
//                      activity.registerReceiver(
//                              chargingStateChangeReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//                  }
//
//                  @Override
//                  public void onCancel(Object arguments) {
//                      // 对面不再接收
//                      activity.unregisterReceiver(chargingStateChangeReceiver);
//                      chargingStateChangeReceiver = null;
//                  }
//              }
//      );
//    }
//    private static BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
//        return new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//            }
//        };
//    }
//
//    @Override
//    public void onListen(Object o, final EventChannel.EventSink eventSink) {
//        Log.e("ying>>FlutterPlugin", "FlutterPluginCounter:onCancel");
//    }
//
//    @Override
//    public void onCancel(Object o) {
//        Log.e("ying>>FlutterPlugin", "FlutterPluginCounter:onCancel");
//    }
//
//}