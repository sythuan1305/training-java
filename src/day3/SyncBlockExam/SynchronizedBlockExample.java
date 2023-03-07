// package day3.SyncBlockExam;

// Khởi tạo class SynchronizedBlockExample dùng để kiểm tra kết quả của việc sử dụng synchronized block
public class SynchronizedBlockExample {
    // biến count dùng để kiểm tra sự đồng bộ của 2 thread khi sử dụng synchronized block cho non-static method
    private int count = 0;

    // phương thức increment() dùng để tăng giá trị của biến count lên 1 đơn vị
    public void increment() {
        // synchronized block sử dụng this để đồng bộ 2 thread
        synchronized (this) {
            this.count++;
        }
    }

    // phương thức decrement() dùng để giảm giá trị của biến count xuống 1 đơn vị
    public void decrement() {
        // synchronized block sử dụng this để đồng bộ 2 thread
        synchronized (this) {
            this.count--;
        }
    }

    // phương thức getCount() dùng để lấy giá trị của biến count
    public int getCount() {
        return this.count;
    }

    //---sync static block example---
    
    // biến staticCount dùng để kiểm tra sự đồng bộ của 2 thread khi sử dụng synchronized block cho static method
    private static int staticCount = 0;

    // phương thức staticIncrementSyc() dùng để tăng giá trị của biến staticCount lên 1 đơn vị
    public  static void staticIncrementSyc()
    {
        // synchronized block sử dụng SynchronizedBlockExample.class để đồng bộ 2 thread
        synchronized (SynchronizedBlockExample.class) {
            staticCount++;
        }
    }

    // phương thức staticDecrementSyc() dùng để giảm giá trị của biến staticCount xuống 1 đơn vị
    public  static void staticDecrementSyc()
    {
        // synchronized block sử dụng SynchronizedBlockExample.class để đồng bộ 2 thread
        synchronized (SynchronizedBlockExample.class) {
            staticCount--;
        }
    }

    // phương thức getStaticCount() dùng để lấy giá trị của biến staticCount
    public static int getStaticCount() {
        return staticCount;
    }

    public static void main(String[] args) {
        SynchronizedBlockExample obj = new SynchronizedBlockExample();

        
        // khởi tạo 2 thread t1 và t2
        Thread t1 = new Thread(() -> {
            // 
            for (int i = 0; i < 100000; i++) { 
                // tăng giá trị của biến count lên 1 đơn vị
                obj.increment();
                // System.out.println("t1: " + obj.getCount());
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                // giảm giá trị của biến count xuống 1 đơn vị
                obj.decrement();
                // System.out.println("t2: " + obj.getCount());
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
        // in ra giá trị của biến count
        System.out.println(obj.getCount());
        // kết quả sẽ là 0 vì 2 thread sử dụng synchronized block để đồng bộ 2 thread

        // sync static block example

        // khởi tạo 2 thread t3 và t4
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                // tăng giá trị của biến staticCount lên 1 đơn vị
                SynchronizedBlockExample.staticIncrementSyc();
                // System.out.println("t3: " + SynchronizedBlockExample.getStaticCount());
            }
        });

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                // giảm giá trị của biến staticCount xuống 1 đơn vị
                SynchronizedBlockExample.staticDecrementSyc();
                // System.out.println("t4: " + SynchronizedBlockExample.getStaticCount());
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

        // in ra giá trị của biến staticCount
        System.out.println(SynchronizedBlockExample.staticCount);
        // kết quả sẽ là 0 vì 2 thread sử dụng synchronized block để đồng bộ 2 thread
    }

}
