package line.javafoundation.producer_consumer;


public class Phone {
    private int id;
//    手机编号

    public Phone(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "手机编号：" + id;
    }
}


 
