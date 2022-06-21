package com.yin.line.javabase.binder;

import android.os.RemoteException;
import android.util.Log;

import com.yin.line.javabase.ICalculate;

public class Calculate extends ICalculate.Stub {
    @Override
    public int add(int num1, int num2) throws RemoteException {
        int pid = android.os.Process.myPid();
        Log.d("yin>>", "当前进程ID为：" + pid + "----" + "这里收到了客户端的Calculate请求");
        Log.d("yin>>", "当前线程ID为：" + Thread.currentThread().getName());
        return num1 + num2;
    }
}
