package line.javafoundation.producer_consumer;

public class Producer implements Runnable {
    private Storage<Phone> storage;

    public Producer(Storage<Phone> storage) {
        this.storage = storage;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            storage.produce(new Phone(i));
            try {
                Thread.sleep(100);
//                每隔1000毫秒生产一个产品
            } catch (InterruptedException e) {
//                TODO Auto -generated catch block e.printStackTrace();
            }
        }
    }
}
