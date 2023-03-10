public class InnerAnonymousClasses {
    // lớp MyInterface là một interface có phương thức doSomething()
    interface MyInterface {
        void doSomething();
    }

    // phương thức myMethod() tạo một đối tượng ẩn danh của lớp MyInterface
    // và gọi phương thức doSomething() của đối tượng ẩn danh

    // class myInterface implements MyInterface{
    //     public void doSomething() {
    //         System.out.println("Doing something...");
    //     }
    // }
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
