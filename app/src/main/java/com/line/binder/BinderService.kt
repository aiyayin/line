package com.line.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import line.binder.BinderPool.BinderPoolImpl


/**
 *
 * @author ying.fu
 * @date 2022/06/20
 * @desc
 * @tapd
 *
 */
class BinderPoolService : Service() {
    private val mBinderPool: BinderPoolImpl = BinderPoolImpl() // 1

    override   fun onBind(intent: Intent?): IBinder {
        Log.d("yin>>", "当前进程ID为：----客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象")
        return mBinderPool
    }

}


