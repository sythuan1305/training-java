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
