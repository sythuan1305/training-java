

public class DefaultMethodExample {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.doSomething(); // Kết quả: Doing something...
        myClass.doSomethingElse(); // Kết quả: Doing something else...
    }
}

interface MyInterface {
    void doSomething();

    default void doSomethingElse() {
        System.out.println("Doing something else...");
    }
}

class MyClass implements MyInterface {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}