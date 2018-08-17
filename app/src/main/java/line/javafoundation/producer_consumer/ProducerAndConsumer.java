package line.javafoundation.producer_consumer;


/**
 * 网上代码 用于理解synchronized
 */
public class ProducerAndConsumer {
    public static void main(String[] args) {
        Storage<Phone> storage = new Storage<Phone>();
        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();
    }
}
