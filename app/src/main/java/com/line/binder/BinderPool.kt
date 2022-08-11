package line.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.os.RemoteException
import android.util.Log
import com.line.binder.BinderPoolService
import com.line.binder.Calculate
import com.line.binder.Speak
import com.line.binder.IBinderPool
import java.util.concurrent.CountDownLatch


/**
 *
 * @author ying.fu
 * @date 2022/06/20
 * @desc
 * @link https://juejin.cn/post/7021795618804727816
 *
 */
class BinderPool private constructor(context: Context) {
    private val mContext: Context
    private var mBinderPool: IBinderPool? = null
    private var mConnectBinderPoolCountDownLatch: CountDownLatch? = null

    @Synchronized
    private fun connectBinderPoolService() {      // 3
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val service = Intent(mContext, BinderPoolService::class.java)
        mContext.bindService(
            service, mBinderPoolConnection,
            Context.BIND_AUTO_CREATE
        )
        try {
            mConnectBinderPoolCountDownLatch?.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun queryBinder(binderCode: Int): IBinder? {          // 4
        Log.d("yin>>", "queryBinder 当前线程ID为：" + Thread.currentThread().name)

        var binder: IBinder? = null
        try {

            binder = mBinderPool?.queryBinder(binderCode)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return binder
    }

    private val mBinderPoolConnection: ServiceConnection = object : ServiceConnection {
        // 5
        override fun onServiceDisconnected(name: ComponentName) {}
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            Log.d("yin>>", "onServiceConnected 当前线程ID为：" + Thread.currentThread().name)
            try {
                mBinderPool?.asBinder()?.linkToDeath(mBinderPoolDeathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            mConnectBinderPoolCountDownLatch?.countDown()
        }
    }
    private val mBinderPoolDeathRecipient: DeathRecipient = object : DeathRecipient {
        // 6
        override fun binderDied() {
            mBinderPool?.asBinder()?.unlinkToDeath(this, 0)
            mBinderPool = null
            connectBinderPoolService()
        }
    }

    class BinderPoolImpl  // 7
        : IBinderPool.Stub() {
        @Throws(RemoteException::class)
        override fun queryBinder(binderCode: Int): IBinder? {
            var binder: IBinder? = null
            when (binderCode) {
                BINDER_SPEAK -> {
                    binder = Speak()
                }
                BINDER_CALCULATE -> {
                    binder = Calculate()
                }
                else -> {}
            }
            return binder
        }
    }

    companion object {
        const val BINDER_SPEAK = 0
        const val BINDER_CALCULATE = 1

        @Volatile
        private var sInstance: BinderPool? = null

        fun getInstance(context: Context): BinderPool {     // 2
            val i = sInstance
            if (i != null) {
                return i
            }
              return synchronized(BinderPool::class) {
                  val i2 = sInstance
                  if (i2 != null) {
                      i2
                  } else {
                      val created =  BinderPool(context)
                      sInstance = created
                      created
                  }
              }

        }
    }

    init {               // 1
        mContext = context.getApplicationContext()
        connectBinderPoolService()
    }
}





