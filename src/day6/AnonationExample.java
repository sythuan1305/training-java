package day6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
@interface MyAnnotation {
    String value();
}

public class AnonationExample {

    public static void main(String[] args) {
//        @NotNull
//        String userName = null;
//
//        List<String> request = new @NotEmpty ArrayList<>(Arrays.stream(args).collect(
//                Collectors.toList()));
    }

}
