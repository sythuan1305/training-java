package day3.SyncBlockExam;

public class SynchronizedBlockExample {
    private int count = 0;

    private static int staticCount = 0;

    public void increment() {
        synchronized (this) {
            this.count++;
        }
    }

    public void decrement() {
        synchronized (this) {
            this.count--;
        }
    }

    // sync static block example
    public  static void staticIncrementSyc()
    {
        synchronized (SynchronizedBlockExample.class) {
            staticCount++;
        }
    }


    public  static void staticDecrementSyc()
    {
        synchronized (SynchronizedBlockExample.class) {
            staticCount--;
        }
    }


    public int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        SynchronizedBlockExample obj = new SynchronizedBlockExample();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                obj.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                obj.decrement();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(obj.getCount()); // kết quả mong đợi là 0

        // sync static block example
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedBlockExample.staticIncrementSyc();
            }
        });

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedBlockExample.staticDecrementSyc();
            }
        });

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(SynchronizedBlockExample.staticCount); // kết quả mong đợi là 0
    }

}
