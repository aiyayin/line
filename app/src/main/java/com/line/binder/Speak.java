package com.line.binder;

import android.os.RemoteException;
import android.util.Log;

import com.line.binder.ISpeak;

public class Speak extends ISpeak.Stub {
    public Speak() {
        Log.d("yin>>", "我被实例化了..............");
    }


    @Override
    public void speak() throws RemoteException {
        int pid = android.os.Process.myPid();
        Log.d("yin>>", "当前进程ID为：" + pid + "-----" + "这里收到了客户端的speak请求");
        Log.d("yin>>", "当前线程ID为：" + Thread.currentThread().getName());
    }
}
