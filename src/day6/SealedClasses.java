public class SealedClasses {

    // sealed class với các class con được phép extend
    public sealed class Shape permits Circle, Rectangle {
        // ...
    }

    // class con với từ khóa final
    // class con không được phép extend
    final class Circle extends Shape {
        // ...
    }

    // class con với từ khóa sealed
    // danh sách các class con được phép extend
    sealed class Rectangle extends Shape permits Square {
        // ...
    }

    // class con với từ khóa non-sealed
    // bất kỳ class nào cũng có thể extend
    non-sealed class Square extends Rectangle {
        // ...
    }

    class unknowClass extends Square {
        // ...
    }


    public static void main(String[] args) {
        SealedClasses sealedClasses = new SealedClasses();
        Shape shape = sealedClasses.new Square();
        System.out.println(shape);

        Shape shape1 = sealedClasses.new unknowClass();
        System.out.println(shape1);
    }
}
