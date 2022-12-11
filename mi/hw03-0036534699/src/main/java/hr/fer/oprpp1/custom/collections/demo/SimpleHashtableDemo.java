package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class SimpleHashtableDemo {
    public static void main(String[] args) {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana

        var iterator = examMarks.iterator();

        while (iterator.hasNext()) {
            var entry = iterator.next();
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        for (var entry : examMarks) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
