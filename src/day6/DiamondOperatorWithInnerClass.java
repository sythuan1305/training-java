package day6;

public class DiamondOperatorWithInnerClass {
    static StringAppender<String> appending = new StringAppender<>() {
        @Override
        public String append(String a, String b) {
            return new StringBuilder(a).append("-").append(b).toString();
        }
    };
    
    public abstract static class StringAppender<T> {
        public abstract T append(String a, String b);
    }

    public static void main(String[] args) {
        // StringAppender<String> appending = new StringAppender<>() {
        //     @Override
        //     public String append(String a, String b) {
        //         return new StringBuilder(a).append("-").append(b).toString();
        //     }
        // };
        System.out.println(appending.append("Hello", "World"));
    }
}
