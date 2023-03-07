public class SyncVarExam {

    // biến count dùng để kiểm tra sự đồng bộ của 2 thread khi sử dụng synchronized block cho non-static method
    private int count = 0;
    // khởi tạo 1 đối tượng lock để đồng bộ 2 thread
    private Object lock = new Object();

    // phương thức increment() dùng để tăng giá trị của biến count lên 1 đơn vị
    public void increment() {
        // synchronized block sử dụng lock để đồng bộ 2 thread
        synchronized (lock) {
            this.count++;
        }
    }

    // phương thức decrement() dùng để giảm giá trị của biến count xuống 1 đơn vị
    public void decrement() {
        // synchronized block sử dụng lock để đồng bộ 2 thread
        synchronized (lock) {
            this.count--;
        }
    }

    // phương thức getCount() dùng để lấy giá trị của biến count
    public int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        SyncVarExam obj = new SyncVarExam();

        // khởi tạo 2 thread t1 và t2
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

        // in ra giá trị của biến count
        System.out.println(obj.getCount());
        // kết quả sẽ là 0 vì 2 thread sử dụng synchronized block để đồng bộ 2 thread
    }

}
