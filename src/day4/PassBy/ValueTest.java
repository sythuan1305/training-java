package day4.PassBy;

public class ValueTest {
    public static void main(String[] args) {
        // khởi tạo 1 biến kiểu int có giá trị là 5
        int a = 5;
        // gọi phương thức increment() và truyền vào biến a
        increment(a);
        // in ra giá trị của biến a
        System.out.println(a); // output: 5
    }
    
    // phương thức increment() dùng để tăng giá trị của biến num lên 1 đơn vị
    public static void increment(int num) {
        num++;
    }
}