package day4.PassBy;

public class RefTest {
    public static void main(String[] args) {
        Person person = new Person("John");
        changeName(person);
        System.out.println(person.getName()); // output: "Jane"
    }

    public static void changeName(Person person) {
        person.setName("Jane");
    }
}
