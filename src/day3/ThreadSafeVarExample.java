import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeVarExample {
    public static void main(String[] args) {
        // khởi tạo 1 đối tượng Counter để kiểm tra sự đồng bộ của 2 thread khi sử dụng synchronized block cho non-static method
        Counter counter = new Counter();

        // khởi tạo 2 thread t1 và t2
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.decrement();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // in ra giá trị của biến count
        System.out.println("Final count value: " + counter.getCount());
        // kết quả sẽ là 0 vì sử dụng AtomicInteger có sẵn các phương thức để đồng bộ 2 thread
    }
}

class Counter {
    // khởi tạo biến count với giá trị mặc định là 0
    private AtomicInteger count = new AtomicInteger(0);

    // phương thức getCount() dùng để lấy giá trị của biến count
    public int getCount() {
        return count.get();
    }

    // phương thức increment() dùng để tăng giá trị của biến count lên 1 đơn vị
    public void increment() {
        count.incrementAndGet();
    }

    // phương thức decrement() dùng để giảm giá trị của biến count xuống 1 đơn vị
    public void decrement() {
        count.decrementAndGet();
    }
    
}
