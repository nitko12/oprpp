package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class HelpCommand implements ShellCommand {

    /**
     * Ispiše sve naredbe ili opis naredbe ako je navedena.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {
        if (arguments.strip() != "") {
            env.writeln("Komanda help ne prima argumente.");
            return ShellStatus.CONTINUE;
        }

        env.writeln("Dostupne naredbe:");

        SortedMap<String, ShellCommand> commands = env.commands();

        for (String command : commands.keySet()) {
            String name = commands.get(command).getCommandName();
            List<String> description = commands.get(command).getCommandDescription();

            env.writeln("  " + name);

            for (String line : description) {
                env.writeln("    " + line);
            }

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
        return "help";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda help ispisuje sve naredbe i opis naredbe ako je navedena.");
    }

}
