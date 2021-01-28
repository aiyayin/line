package line.javafoundation;

import android.util.Log;

public class Synchronized {
    private static Object lock = new Object();
    private static Object lockb = new Object();
    public static class ClassA {

        public  void methodA() {
            try {
                synchronized (lock) {
//                System.out.println("yin>" + "methodA 1111 " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                    System.out.println("yin>" + "methodA start " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                    Thread.sleep(5000);
                    System.out.println("yin>" + "methodA end " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public  void methodB() {
            try {
                synchronized (lockb) {
//            System.out.println("yin>" + "methodB  " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                    System.out.println("yin>" + "methodB start " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                    Thread.sleep(5000);
                    System.out.println("yin>" + "methodB end " + Thread.currentThread().getName() + " at time " + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        ClassA A = new ClassA();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                A.methodA();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                A.methodB();
                A.methodA();
            }
        });

        try {
            thread.start();
            Thread.sleep(2000);
            thread2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * yin>methodA 1111 Thread-0 at time 1601369960654
         * yin>methodA start Thread-0 at time 1601369960654
         * yin>methodB  Thread-1 at time 1601369962654
         * yin>methodA 1111 Thread-1 at time 1601369962654
         * yin>methodA end Thread-0 at time 1601369965659
         * yin>methodA start Thread-1 at time 1601369965659
         * yin>methodA end Thread-1 at time 1601369970659
         */
    }
}
