package line.javafoundation.producer_consumer;

/**
 *
 * Created by ying.fu on 2018/8/9.
 */

public class ProducerAndConsumer {
    public static void main(String[] args) {
        Storage<Phone> storage = new Storage<Phone>();
        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();
    }
}
