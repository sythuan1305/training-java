package day4.PassBy;

public class RefTest {
    public static void main(String[] args) {
        // khởi tạo 1 đối tượng Person
        Person person = new Person("John");
        // gọi phương thức changeName() và truyền vào đối tượng person
        changeName(person);
        // in ra tên của đối tượng person
        System.out.println(person.getName()); // output: "Jane"
    }

    public static void changeName(Person person) {
        person.setName("Jane");
    }
}
