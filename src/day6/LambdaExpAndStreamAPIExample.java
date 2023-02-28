package day6;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaExpAndStreamAPIExample {

    public static void main(String[] args) {
        // Stream API
        List<Integer> numbers = Arrays.asList(5, 10, 15, 20, 25, 30);

        List<Integer> result = numbers.stream()
                .filter(number -> number > 10)
                .collect(Collectors.toList());

        System.out.println(result); // Kết quả: [15, 20, 25, 30]

        // parallelStream
        List<Integer> result2 = numbers.parallelStream()
                .filter(number -> number > 10)
                .collect(Collectors.toList());

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

@FunctionalInterface
interface Processor {
  int getStringLength(String str);
}


