package day6.LocalTypeVar;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LocalTypeVarLambda {
    public void explicitTypes() {
        var Roland = new Person("Roland", "Deschain");
        var Susan = new Person("Susan", "Delgado");
        var Eddie = new Person("Eddie", "Dean");
        var Detta = new Person("Detta", "Walker");
        var Jake = new Person("Jake", "Chambers");

        List.of(Roland, Susan, Eddie, Detta, Jake)
                .stream()
                .filter((var x) -> x.name.contains("a")) // == .filter(x -> x.name.contains("a")) || .filter((Person x) -> x.name.contains("a"))
                .collect(Collectors.toList()).forEach(p -> System.out.println(p.name));
    }

    public static void main(String[] args) {
        LocalTypeVarLambda localTypeVarLambda = new LocalTypeVarLambda();
        localTypeVarLambda.explicitTypes();
    }
}
