package day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class TryWithResources {
    public static void main(String[] args) {

        // close a resource in finally block
        BufferedReader br = new BufferedReader(
                new StringReader("Hello world example!"));
        try {
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // close a resource in try-with-resources block
        try (BufferedReader br2 = new BufferedReader(
                new StringReader("Hello world example!"))) {
            System.out.println(br2.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
