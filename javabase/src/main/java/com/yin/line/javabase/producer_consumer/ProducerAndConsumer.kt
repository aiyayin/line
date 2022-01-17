package line.javafoundation.producer_consumer

/**
 * 网上代码 用于理解synchronized
 */
object ProducerAndConsumer {
    @JvmStatic
    fun main(args: Array<String>) {
        val storage = Storage<Phone>()
        Thread(Producer(storage)).start()
        Thread(Consumer(storage)).start()
    }
}