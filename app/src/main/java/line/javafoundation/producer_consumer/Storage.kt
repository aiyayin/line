package line.javafoundation.producer_consumer

import java.util.*

class Storage<T> {
    private val lock = Object()
    private var index = 0

    //    最大容量
    private val storageList: MutableList<T> = ArrayList(MAX)

    //    存储集合
    @Synchronized
    fun produce(item: T) {
        while (index >= MAX) {
//            判断仓库满了，则等待。
            try {
                println("仓库满了，等待中...")
                lock.wait()
                println("仓库不满了，开始生产")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        println("生产>>" + item.toString())
        storageList.add(item)
        index++
        //        先添加item，在进行加1操作
        lock.notify()
        // 唤醒在此对象监视器上等待的单个线程，即消费者线程
    }

    @Synchronized
    fun consume(): T {
        while (index <= 0) {
//            判断仓库空了，则等待。
            try {
                println("仓库为空，等待中...")
                lock.wait()
                println("仓库不为空，开始消费")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        index--
        //        先进行减1操作，再remove
        val item = storageList.removeAt(index)
        println("消费>>" + item.toString())
        lock.notify()
        return item
    }

    companion object {
        private const val MAX = 10
    }
}