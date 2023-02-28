package day6;

public class SealedClasses {
    public sealed class Shape permits Circle, Rectangle {
        // ...
    }

    final class Circle extends Shape {
        // ...
    }

    final class Rectangle extends Shape {
        // ...
    }
}
