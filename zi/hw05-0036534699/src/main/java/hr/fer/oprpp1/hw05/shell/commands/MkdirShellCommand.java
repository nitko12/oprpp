package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand {

    /**
     * Izvršava naredbu mkdir.
     * Napraavi novi direktorij.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {
        String[] args = ArgumentSplitter.splitArguments(arguments);

        if (args.length != 1) {
            env.writeln("Komanda mkdir prima jedan argument.");
            return ShellStatus.CONTINUE;
        }

        try {

            Files.createDirectories(Paths.get(args[0]));

        } catch (Exception e) {
            env.writeln("Neuspješno stvaranje direktorija.");
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
        return "mkdir";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda mkdir stvara novi direktorij.");
    }

}
