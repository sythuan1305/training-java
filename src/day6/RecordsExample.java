

public record RecordsExample(String name, String lastname) {

    public static void main(String[] args) {
        RecordsExample recordsExample = new RecordsExample("Nguyen", "Van A");
        System.out.println(recordsExample.name());
        System.out.println(recordsExample.lastname());
        recordsExample.setName("Nguyen Van B");
        System.out.println(recordsExample.name());
    }

}
