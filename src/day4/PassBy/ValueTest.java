package day4.PassBy;

public class ValueTest {
    public static void main(String[] args) {
        int a = 5;
        increment(a);
        System.out.println(a); // output: 5
    }
    
    public static void increment(int num) {
        num++;
    }
}