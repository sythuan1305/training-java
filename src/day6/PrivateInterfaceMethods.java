package day6;

public class PrivateInterfaceMethods {

    public static void main(String[] args) {
        PrivateInterfaceMethods privateInterfaceMethods = new PrivateInterfaceMethods();
        MyInterface myObject = privateInterfaceMethods.new MyClass();
        myObject.doSomethingElse();
    }

    public interface MyInterface {
        void doSomething();

        default void doSomethingElse() {
            doSomethingPrivate();
        }

        // phương thức doSomethingPrivate() là một phương thức private của interface
        // được sử dụng trong phương thức default doSomethingElse()
        // không thể được gọi bên ngoài interface
        // không thể được override bởi các lớp implement interface 
        private void doSomethingPrivate() {
            System.out.println("Doing something privately...");
        }
    }

    public class MyClass implements MyInterface {
        @Override
        public void doSomething() {
            System.out.println("Doing something...");
        }
    }
}
