package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;

public class CollectionMIDemo {
    public static void main(String[] args) {
        Collection<String> prva = new ArrayIndexedCollection<>();
        Collection<Integer> druga = new ArrayIndexedCollection<>();

        prva.add("Ivo");
        prva.add("Ivka");

        prva.copyTransformedIntoIfAllowed(druga,
                Object::hashCode,
                n -> n.intValue() % 2 == 0);

        for (Object o : druga.toArray()) {
            System.out.println(o);
        }
    }
}
