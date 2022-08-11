package com.yin.javalib.producer_consumer

class Producer(private val storage: Storage<Phone>) : Runnable {
    override fun run() {
        for (i in 0..19) {
            storage.produce(Phone(i))
            try {
                Thread.sleep(1000)
                //                每隔1000毫秒生产一个产品
            } catch (e: InterruptedException) {
            }
        }
    }

}