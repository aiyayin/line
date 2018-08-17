package line.javafoundation.producer_consumer;


public class Consumer implements Runnable {
    private Storage<Phone> storage;

    public Consumer(Storage<Phone> storage) {
        this.storage = storage;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            storage.consume();
            try {
                Thread.sleep(1000);
//                每隔1000毫秒消费一个
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


 