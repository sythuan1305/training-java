import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class LambdaExpAndStreamAPIExample {

    public static void main(String[] args) {
        // Stream API
        List<Integer> numbers = Arrays.asList(5, 10, 15, 20, 25, 30);

        // khai báo biến result sử dụng stream API để lọc các phần tử trong list numbers có giá trị lớn hơn 10
        List<Integer> result = numbers.stream() // tạo stream từ list numbers
                .filter(number -> number > 10) // lọc các phần tử có giá trị lớn hơn 10
                .collect(Collectors.toList());  // chuyển stream thành list

        System.out.println(result); // Kết quả: [15, 20, 25, 30]

        // parallelStream

        List<Integer> result2 = numbers.parallelStream() // tạo parallelStream từ list numbers
                .filter(number -> number > 10)// lọc các phần tử có giá trị lớn hơn 10
                .collect(Collectors.toList()); // chuyển stream thành list

        System.out.println(result2); // Kết quả: [15, 20, 25, 30]


        // Lambda expression
        Processor processor = str -> str.length();

        // Function interface
        System.out.println(processor.getStringLength("Hello")); // Kết quả: 5

        // pass a lambda expression to a method
        printString("Hello", processor);
    }

    private static void printString(String str, Processor processor) {
        System.out.println(processor.getStringLength(str));
    }

}
// annotation để chỉ ra interface này là functional interface
// nghĩa là interface này chỉ có 1 phương thức abstract
// nếu có nhiều hơn 1 phương thức abstract thì sẽ bị lỗi
// nếu có 1 phương thức abstract và nhiều phương thức default thì vẫn không bị lỗi
// Dùng để định nghĩa interface có thể sử dụng lambda expression
@FunctionalInterface 
interface Processor {
  int getStringLength(String str);
}


