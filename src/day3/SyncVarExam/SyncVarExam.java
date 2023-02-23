package day3.SyncVarExam;

public class SyncVarExam {

    private int count = 0;
    private Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            this.count++;
        }
    }

    public void decrement() {
        synchronized (lock) {
            this.count--;
        }
    }

    public int getCount() {
        synchronized (this) {
            return this.count;
        }
    }

    public static void main(String[] args) {
        SyncVarExam obj = new SyncVarExam();

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

        System.out.println(obj.getCount()); // 0
    }

}
