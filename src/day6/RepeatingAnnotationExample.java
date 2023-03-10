import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RepeatingAnnotationExample {
    // 
    @Retention(RetentionPolicy.RUNTIME) // RetentionPolicy.RUNTIME: Annotation được giữ lại trong thời gian chạy của chương trình 
    // @Target({ ElementType.TYPE, ElementType.METHOD }) // ElementType.TYPE: Annotation được áp dụng cho class - ElementType.METHOD: Annotation được áp dụng cho method
    @Repeatable(Authors.class) // Annotation có thể được lặp lại nhiều lần với container là Authors
    // cho phép lặp lại nhiều lần annotation Author trên một element
    public @interface Author {
        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    // @Target({ ElementType.TYPE, ElementType.METHOD })
    public @interface Authors {
        Author[] value(); // danh sách các element có kiểu dữ liệu là Author trong container Authors
    }

    @Author(name = "John Doe")
    @Author(name = "Jane Doe")
    public class Book {

        public void print() {
            System.out.println("This book is written by John Doe and Jane Doe");
        }
    }

    public static void main(String[] args) {
        RepeatingAnnotationExample example = new RepeatingAnnotationExample();
        Book book = example.new Book();
        Author[] authors = book.getClass().getAnnotationsByType(Author.class);
        System.out.println("Authors of the book:");
        for (Author author : authors) {
            System.out.println(author.name());
        }

        book.print();
    }
}
