package com.yin.javalib.producer_consumer

class Consumer(private val storage: Storage<Phone>) : Runnable {
    override fun run() {
        for (i in 0..19) {
            storage.consume()
            try {
                Thread.sleep(1000)
                //                每隔1000毫秒消费一个
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}