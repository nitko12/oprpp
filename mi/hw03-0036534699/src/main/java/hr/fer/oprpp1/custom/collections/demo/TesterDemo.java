package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

public class TesterDemo {
    public static void main(String[] args) {
        class EvenIntegerTester implements Tester<Integer> {
            public boolean test(Integer obj) {
                // if (!(obj instanceof Integer))
                // return false;
                Integer i = (Integer) obj;
                return i % 2 == 0;
            }
        }

        Tester<Integer> t = new EvenIntegerTester();
        // System.out.println(t.test("Ivo")); // false -> error
        System.out.println(t.test(22)); // true
        System.out.println(t.test(3)); // false
    }
}
