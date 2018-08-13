package line.javafoundation.producer_consumer;

/**
 * Created by ying.fu on 2018/8/9.
 */

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


 
