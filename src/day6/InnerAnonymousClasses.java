package day6;

public class InnerAnonymousClasses {
    public void myMethod() {
        MyInterface myInterface = new MyInterface() {
            @Override
            public void doSomething() {
                System.out.println("Doing something...");
            }
        };
        myInterface.doSomething();
    }

    public static void main(String[] args) {
        InnerAnonymousClasses innerAnonymousClasses = new InnerAnonymousClasses();
        innerAnonymousClasses.myMethod();
    }
}

interface MyInterface {
    void doSomething();
}