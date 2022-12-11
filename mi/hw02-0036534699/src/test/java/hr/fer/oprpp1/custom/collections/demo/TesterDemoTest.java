package hr.fer.oprpp1.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.Tester;

public class TesterDemoTest {
    @Test
    public void test() {
        class EvenIntegerTester implements Tester {
            public boolean test(Object obj) {
                if (!(obj instanceof Integer))
                    return false;
                Integer i = (Integer) obj;
                return i % 2 == 0;
            }
        }

        Tester t = new EvenIntegerTester();
        assertEquals(false, t.test("Ivo"));
        assertEquals(true, t.test(22));
        assertEquals(false, t.test(3));
    }
}
