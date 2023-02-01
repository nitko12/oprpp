package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class ExitShellCommand implements ShellCommand {

    /**
     * Izlazi iz shell-a.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {

        if (arguments.strip() != "") {
            env.writeln("Komanda exit ne prima argumente.");
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.TERMINATE;
    }

    /**
     * Vraća naziv naredbe.
     * 
     * @return String
     */
    @Override
    public String getCommandName() {
        return "exit";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda exit završava rad naredbenog retka.");
    }

}
