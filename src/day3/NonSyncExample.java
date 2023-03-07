public class NonSyncExample {
    // class có tên là MyThread implements Runnable
    class MyThread implements Runnable {
        // khai báo biến unsafe có kiểu dữ liệu là UnSafeSequence
        private UnSafeSequence unsafe;
    
        // constructor có tham số là unsafe
        public MyThread(UnSafeSequence unsafe) {
            this.unsafe = unsafe;
        }
    
        // override lại phương thức run() của interface Runnable
        // trong phương thức run() gọi phương thức NextValue() của biến unsafe
        // phương thức NextValue() dùng để tăng giá trị của biến value lên 1 đơn vị
        // phương thức run() được gọi khi thread được khởi tạo
        @Override
        public void run() {
            unsafe.NextValue();
        }
    }
    
    // Khởi tạo class UnSafeSequence
    public class UnSafeSequence {
        private int value;
    
        // constructor của class UnSafeSequence không có tham số với giá trị mặc định của biến value là 0
        UnSafeSequence() {
            value = 0;
        }
    
        // phương thức NextValue() dùng để tăng giá trị của biến value lên 1 đơn vị
        public  void  NextValue() {
            value++;
        }
    
        // phương thức getValue() dùng để lấy giá trị của biến value
        public int getValue() {
            return value;
        }
    }
    
    
        public static void main(String[] args) {
            // khởi tạo biến seq có kiểu dữ liệu là UnSafeSequence để kiểm tra giá trị của biến value sau khi tăng lên 30 đơn vị
            NonSyncExample example = new NonSyncExample();
            UnSafeSequence seq = example.new UnSafeSequence();
    
            // khởi tạo 30 thread với mỗi thread và dùng cùng 1 biến seq
            for (int i = 0; i < 30; i++) {
                MyThread thread = example.new MyThread(seq);
                // khởi tạo và chạy thread
                new Thread(thread).start();
            }
    
            // in ra giá trị của biến value
            System.out.println(seq.getValue());
            // kết quả sẽ không phải là 30 vì các thread chạy song song nên sẽ có trường hợp 2 thread cùng tăng giá trị của biến value lên 1 đơn vị
        
    }
}