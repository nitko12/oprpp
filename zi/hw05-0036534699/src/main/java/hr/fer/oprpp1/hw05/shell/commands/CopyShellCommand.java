package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand {

    /**
     * Kopira datoteku iz prvog argumenta u drugi argument.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {

        String[] args = ArgumentSplitter.splitArguments(arguments);

        if (args.length != 2) {
            env.writeln("Krivi broj argumenata.");
            return ShellStatus.CONTINUE;
        }

        if (args[0].equals(args[1])) {
            env.writeln("Ne možete kopirati datoteku u samu sebe.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.copy(Paths.get(args[0]), Paths.get(args[1]));
        } catch (Exception e) {
            env.writeln("Ne mogu kopirati datoteku " + args[0] + " u datoteku " + args[1]);
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
        return "copy";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda copy uzima dva argumenta.",
                "Prvi argument je ime datoteke koju se kopira",
                "Drugi argument je ime datoteke u koju se kopira");
    }

}
