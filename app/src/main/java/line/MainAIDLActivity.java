package line;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.yingfu.line.R;
import com.yin.line.javabase.ICalculate;
import com.yin.line.javabase.ISpeak;
import com.yin.line.javabase.binder.BinderPool;

public class MainAIDLActivity extends Activity {

    private ISpeak mSpeak;
    private ICalculate mCalculate;
    private int pid = android.os.Process.myPid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_aidl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                startWork();
            }
        }).start();
    }

    private void startWork() {
        Log.d("yin>>", "当前进程ID为：" + pid);
        Log.d("yin>>", "当前线程ID为：" + Thread.currentThread().getName());
        Log.d("yin>>", "获取BinderPool对象............");
        BinderPool binderPool = BinderPool.Companion.getInstance(MainAIDLActivity.this);     // 1
        Log.d("yin>>", "获取speakBinder对象...........");
        IBinder speakBinder = binderPool.queryBinder(BinderPool.BINDER_SPEAK);  // 2
        Log.d("yin>>", "获取speak的代理对象............");
        mSpeak = (ISpeak) ISpeak.Stub.asInterface(speakBinder);    // 3 
        try {
            mSpeak.speak();     // 4
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d("yin>>", "获取calculateBinder对象...........");
        IBinder calculateBinder = binderPool.queryBinder(BinderPool.BINDER_CALCULATE);
        Log.d("yin>>", "获取calculate的代理对象............");
        mCalculate = (ICalculate) ICalculate.Stub.asInterface(calculateBinder);
        try {
            Log.d("yin>>", "" + mCalculate.add(5, 6));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

