import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolingExample {
    public static void main(String[] args) throws InterruptedException {
        // Tạo một thread pool với kích thước 5
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Thêm 10 công việc vào thread pool để thực thi đồng thời
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("Task " + (i + 1));
            executor.execute(worker);
        }

        // Đóng thread pool sau khi các công việc được hoàn thành
        executor.shutdown();
        
        // Chờ cho đến khi tất cả các công việc được hoàn thành và thread pool được đóng
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("All tasks have been completed");
    }
}

class WorkerThread implements Runnable {
    // Tên của công việc
    private String taskName;

    // 
    public WorkerThread(String taskName) {
        this.taskName = taskName;
    }

    // Phương thức run() sẽ được gọi khi thread được khởi chạy
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start executing " + taskName);
        processTask();
        System.out.println(Thread.currentThread().getName() + " End executing " + taskName);
    }

    // Phương thức này sẽ được gọi để thực hiện công việc
    private void processTask() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
