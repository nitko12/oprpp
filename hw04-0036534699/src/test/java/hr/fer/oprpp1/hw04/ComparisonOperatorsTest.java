package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.StudentDB;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class ComparisonOperatorsTest {

    @Test
    public void testEquals() {

        assertTrue(ComparisonOperators.EQUALS.satisfied("a", "a"));
        assertFalse(ComparisonOperators.EQUALS.satisfied("a", "b"));
    }

    @Test
    public void testNotEquals() {

        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("a", "a"));
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("a", "b"));
    }

    @Test
    public void testLess() {

        assertTrue(ComparisonOperators.LESS.satisfied("a", "b"));
        assertFalse(ComparisonOperators.LESS.satisfied("b", "a"));
        assertFalse(ComparisonOperators.LESS.satisfied("a", "a"));
    }

    @Test
    public void testLessOrEquals() {

        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("a", "b"));
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("b", "a"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("a", "a"));
    }

    @Test
    public void testGreater() {

        assertFalse(ComparisonOperators.GREATER.satisfied("a", "b"));
        assertTrue(ComparisonOperators.GREATER.satisfied("b", "a"));
        assertFalse(ComparisonOperators.GREATER.satisfied("a", "a"));
    }

    @Test
    public void testGreaterOrEquals() {

        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("a", "b"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("b", "a"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("a", "a"));
    }

    @Test
    public void testLike() {

        assertTrue(ComparisonOperators.LIKE.satisfied("a", "a"));
        assertTrue(ComparisonOperators.LIKE.satisfied("a", "*"));
        assertTrue(ComparisonOperators.LIKE.satisfied("a", "a*"));
        assertTrue(ComparisonOperators.LIKE.satisfied("a", "*a"));
        assertThrows(IllegalArgumentException.class, () -> ComparisonOperators.LIKE.satisfied("a", "a**"));

        assertFalse(ComparisonOperators.LIKE.satisfied("a", "b"));
        assertFalse(ComparisonOperators.LIKE.satisfied("a", "b*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("a", "*b"));
        assertFalse(ComparisonOperators.LIKE.satisfied("aaa", "aa*aa"));

    }

}
