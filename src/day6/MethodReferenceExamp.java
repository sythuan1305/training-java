import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
public class MethodReferenceExamp {

    public static void main(String[] args) {
        //--Reference to a static method--
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Sử dụng Method Reference để in ra các phần tử của danh sách
        // numbers.forEach(n -> System.out.print(n));
        numbers.forEach(System.out::print);
        System.out.println();

        //--Reference to an instance method of a particular object--
        String str = "Hello world";

        int length = str.length();

        System.out.println(length); // Kết quả: 11

        //--Reference to an instance method of an arbitrary object of a particular type--
        List<String> words = Arrays.asList("Java", "is", "awesome");

        // Sử dụng Method Reference để sắp xếp danh sách các từ theo thứ tự tăng dần
        // words.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        words.sort(String::compareToIgnoreCase);

        System.out.println(words); // Kết quả: [awesome, is, Java]

        //--Reference to a constructor--
        // Tạo một danh sách các số nguyên
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);

        // Sử dụng Reference to a Constructor để tạo một danh sách mới với các số chia hết cho 2
        // ArrayList::new == () -> new ArrayList()
        List<Integer> newNumbers = numbers1.stream().filter(n -> n % 2 == 0)
                .collect(Collectors.toCollection(ArrayList::new));
                // .collect(Collectors.toCollection(() -> new ArrayList<>()));

        System.out.println(newNumbers); // Kết quả: [2, 4]
    }

}
