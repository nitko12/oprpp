package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {

    /**
     * Ispiše imena svih podržanih charsetova.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {

        if (arguments.length() != 0) {
            env.writeln("Komanda charsets ne prima argumente.");
            return ShellStatus.CONTINUE;
        }

        env.writeln("Supported charsets for your Java platform:");
        for (String charset : java.nio.charset.Charset.availableCharsets().keySet()) {
            env.writeln(charset);
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Vraća naziv naredbe.
     * 
     * @return String
     */
    @Override
    public String getCommandName() {
        return "charsets";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda charsets ispisuje imena svih podržanih charsetova.");
    }

}
