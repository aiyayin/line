package com.example.backstack;

import android.app.Activity;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.PluginRegistry;

/**
 * BackstackPlugin
 */
public class BackstackPlugin implements MethodCallHandler {
    public static String CHANNEL = "line/plugin/backstack";

    public static MethodChannel channel;

    private Activity activity;
    public static int routeCounter = 0;

    private BackstackPlugin(Activity activity) {
        this.activity = activity;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        channel = new MethodChannel(registrar.messenger(), CHANNEL);
        BackstackPlugin instance = new BackstackPlugin(registrar.activity());
        //setMethodCallHandler在此通道上接收方法调用的回调
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {

        //通过MethodCall可以获取参数和方法名，然后再寻找对应的平台业务，本案例做了2个跳转的业务

        //接收来自flutter的指令oneAct
        if (call.method.equals("oneAct")) {

            //跳转到指定Activity
            Log.e("ying>>>", "onMethodCall: oneAct");
            //返回给flutter的参数
            result.success("success");
        }
        //接收来自flutter的指令twoAct
        else if (call.method.equals("twoAct")) {

            //解析参数
            String text = call.argument("flutter");
            if (text != null)
                routeCounter = Integer.parseInt(text);
            //带参数跳转到指定Activity
            Log.e("ying>>>", "onMethodCall: twoAct text  : " + text);

            //返回给flutter的参数
            result.success("success");
        } else {
            result.notImplemented();
        }
    }

}

