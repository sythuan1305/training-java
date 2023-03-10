public class PatternMatching {
    public static void main(String[] args) {
        Object obj = "Hello, world!";
    
        // pattern matching
        if (obj instanceof String s) {
            System.out.println("The length of the string is " + s.length());
        }
    }
}
