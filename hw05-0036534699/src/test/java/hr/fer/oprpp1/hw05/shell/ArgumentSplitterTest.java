package hr.fer.oprpp1.hw05.shell;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArgumentSplitterTest {

    @Test
    void testSplitArgument() {
        String[] args = ArgumentSplitter.splitArguments(
                "C:\\Documents\\Users\\javko \"C:\\Documents and Settings\\Users\\javko\\Documents\\javko.txt\"");
        assertEquals(2, args.length);
        assertEquals("C:\\Documents\\Users\\javko", args[0]);
        assertEquals("C:\\Documents and Settings\\Users\\javko\\Documents\\javko.txt", args[1]);
    }
}
