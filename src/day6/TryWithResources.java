import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
public class TryWithResources {
    public static void main(String[] args) {

        // đóng một tài nguyên trong khối try-catch-finally

        // biến br được khai báo bên ngoài khối try
        BufferedReader br = new BufferedReader(
                new StringReader("Hello world example!"));
        try {
            // đọc dòng đầu tiên từ BufferedReader
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // đóng BufferedReader
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // đóng một tài nguyên trong khối try-with-resources
        try (// biến br2 được khai báo trong khối try
            BufferedReader br2 = new BufferedReader(
                new StringReader("Hello world example!"))) {
            // đọc dòng đầu tiên từ BufferedReader
            System.out.println(br2.readLine());
            // đóng BufferedReader tự động
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
