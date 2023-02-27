package day6.LocalTypeVar;

import java.util.List;

public class LocalTypeVar {
    public void varTypes() {
        var Roland = new Person("Roland", "Deschain");
        var Susan = new Person("Susan", "Delgado");
        var Eddie = new Person("Eddie", "Dean");
        var Detta = new Person("Detta", "Walker");
        var Jake = new Person("Jake", "Chambers");

        var persons = List.of(Roland, Susan, Eddie, Detta, Jake);

        for (var person : persons) {
            System.out.println(person.name + " - " + person.lastname);
        }
    }

    public static void main(String[] args) {
        LocalTypeVar localTypeVar = new LocalTypeVar();
        localTypeVar.varTypes();
    }
}
