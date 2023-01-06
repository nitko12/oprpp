package hr.fer.oprpp1.hw05.shell;

public class ArgumentSplitter {

    /**
     * Metoda koja razdvaja argumente shella.
     * Postuje navodnike i razmake.
     * 
     * @param arguments
     * @return String[]
     */
    public static String[] splitArguments(String arguments) {

        // https://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
        String[] args = arguments.split("(?<!(\"|').{0,255}) | (?!.*\\1.*)");

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("\"") && args[i].endsWith("\"")) {
                args[i] = args[i].substring(1, args[i].length() - 1);
            }
        }

        return args;
    }
}
