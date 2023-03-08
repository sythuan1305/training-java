//https://ramj2ee.blogspot.com/2016/05/java-tutorial-java-string-vs_30.html
public class StringMemoryTest {

    // 1 KB = 1024 Bytes    
    private static final int KBinBytes = 1024;

    /**
     * thực hiện GC 10 lần và sleep 100ms mỗi lần
     */
    private void performGC() {
        for (int i = 0; i < 10; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Lấy số lượng bộ nhớ đã sử dụng trong Java Virtual Machine
     * 
     * @return long
     */
    private long memoryUsed() {
        return ((Runtime.getRuntime().totalMemory() / KBinBytes) - (Runtime
                .getRuntime().freeMemory() / KBinBytes));
    }

    /**
     * Kiểm tra bộ nhớ sử dụng bởi String
     * 
     */
    public void testStringMemory() {
        // Hiển thị tổng bộ nhớ
        System.out
                .println("Initial Total memory in Java virtual machine in KBs="
                        + Runtime.getRuntime().totalMemory()
                                / KBinBytes);
        // Hiển thị bộ nhớ trống
        System.out
                .println("Initial Free memory in Java virtual machine in KBs="
                        + Runtime.getRuntime().freeMemory()
                                / KBinBytes);
        
        long initialmemory = memoryUsed(); // Lấy bộ nhớ đã sử dụng
        long startTime = System.currentTimeMillis(); // Lấy thời gian bắt đầu
        // Tạo 1 String rỗng
        String str = "";
        // Thêm 50000 lần chuỗi "Hello" vào String
        for (int i = 1; i < 50000; i++) {
            str = str + "Hello";
        }
        // Lấy thời gian kết thúc
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime; // Tính thời gian thực hiện
        // Hiển thị thời gian thực hiện
        System.out.println("Time taken to complete the process in MilliSeconds:"
                + elapsedTime);
        // Hiển thị bộ nhớ đã sử dụng
        System.out.println("Memory used by String in KBs="
                + (memoryUsed() - initialmemory));
        str = null;
        // Thực hiện GC
        performGC();
        // Hiển thị bộ nhớ trống
        System.out.println("Free Memory after GC in KBs="
                + Runtime.getRuntime().freeMemory() / KBinBytes);

    }

    /**
     * Kiểm tra bộ nhớ sử dụng bởi StringBuilder
     * 
     */
    public void testStringBuilderMemory() {
        // Hiển thị tổng bộ nhớ
        System.out
                .println("Initial Total memory in Java virtual machine in KBs="
                        + Runtime.getRuntime().totalMemory()
                                / KBinBytes);
        // Hiển thị bộ nhớ trống
        System.out
                .println("Initial Free memory in Java virtual machine in KBs="
                        + Runtime.getRuntime().freeMemory()
                                / KBinBytes);
        // Lấy bộ nhớ đã sử dụng
        long initialmemory = memoryUsed();
        long startTime = System.currentTimeMillis();// Lấy thời gian bắt đầu
        // Tạo 1 StringBuilder rỗng
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 50000; i++) {
                // Thêm 50000 lần chuỗi "Hello" vào StringBuilder
            sb.append("Hello");
        }
        long stopTime = System.currentTimeMillis(); // Lấy thời gian kết thúc
        long elapsedTime = stopTime - startTime;// Tính thời gian thực hiện
        // Hiển thị thời gian thực hiện
        System.out
                .println("Time taken to complete the process in MilliSeconds:"
                        + elapsedTime);
        // Hiển thị bộ nhớ đã sử dụng
        System.out.println("Memory used by StringBuilder in KBs="
                + (memoryUsed() - initialmemory));
        sb = null;
        // Thực hiện GC
        performGC();
        // Hiển thị bộ nhớ trống
        System.out.println("Free Memory after GC in KBs="
                + Runtime.getRuntime().freeMemory() / KBinBytes);

    }

    /**
     * Method to test StringBuffer object memory consumption
     * in a BIG loop
     */
    // public void testStringBufferMemory() {
    //     System.out
    //             .println("Initial Total memory in Java virtual machine in KBs="
    //                     + Runtime.getRuntime().totalMemory()
    //                             / KBinBytes);
    //     System.out
    //             .println("Initial Free memory in Java virtual machine in KBs="
    //                     + Runtime.getRuntime().freeMemory()
    //                             / KBinBytes);
    //     long initialmemory = memoryUsed();
    //     long startTime = System.currentTimeMillis();
    //     StringBuffer sb = new StringBuffer();

    //     for (int i = 1; i < 50000; i++) {
    //         sb.append("Hello");
    //     }
    //     long stopTime = System.currentTimeMillis();
    //     long elapsedTime = stopTime - startTime;
    //     System.out.println("Time taken to complete the process in MilliSeconds:"
    //             + elapsedTime);
    //     System.out.println("Memory used by StringBuffer in KBs="
    //             + (memoryUsed() - initialmemory));
    //     sb = null;
    //     performGC();
    //     System.out.println("Free Memory after GC in KBs="
    //             + Runtime.getRuntime().freeMemory() / KBinBytes);

    // }

    /**
     * Main class
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Hiển thị bộ nhớ tối đa
        System.out
                .println("Initial Max memory in Java virtual machine in KBs="
                        + Runtime.getRuntime().maxMemory()
                                / KBinBytes);
        StringMemoryTest memoryTest = new StringMemoryTest();

        // Kiểm tra bộ nhớ sử dụng bởi String, StringBuilder
        System.out.println(".....................................................\nString memory test\n");
        memoryTest.testStringMemory();
        // System.out.println(".................................................\nStringBuffer memory test\n");
        // memoryTest.testStringBufferMemory();
        System.out.println("...............................................\nStringBuilder memory test\n");
        memoryTest.testStringBuilderMemory();
    }
}