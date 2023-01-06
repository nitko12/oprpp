package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {

    /**
     * Metoda koja ispisuje stablo direktorija
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {
        // print tree view

        String[] parts = ArgumentSplitter.splitArguments(arguments);

        if (parts.length != 1) {
            env.writeln("Komanda tree prima jedan argument.");
            return ShellStatus.CONTINUE;
        }

        Path path = Paths.get(parts[0]);

        if (!Files.isDirectory(path)) {
            env.writeln("Argument mora biti putanja do direktorija.");
            return ShellStatus.CONTINUE;
        }

        try {
            printTreeRecursive(path, env);
        } catch (IOException e) {
            env.writeln("Neuspješno čitanje direktorija.");
        }

        // The tree command expects a single argument: directory name and prints a tree
        // (each directory level shifts output two charatcers to the right).

        return ShellStatus.CONTINUE;
    }

    int depth = 0;

    /**
     * Metoda koja rekurzivno ispisuje stablo direktorija
     * 
     * @param path
     * @param env
     * @throws IOException
     */
    private void printTreeRecursive(Path path, Enviroment env) throws IOException {

        // System.out.println(depth);
        // System.out.println(path);

        if (Files.isDirectory(path)) {
            env.writeln(" ".repeat(depth) + path.getFileName());
            depth += 1;

            for (Path p : Files.newDirectoryStream(path)) {
                printTreeRecursive(p, env);
            }

            depth -= 1;
        } else {
            env.writeln(" ".repeat(depth) + path.getFileName());
        }

    }

    /**
     * Metoda koja vraća ime naredbe
     * 
     * @return String
     */
    @Override
    public String getCommandName() {
        return "tree";
    }

    /**
     * Vraca opis naredbe
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda tree ispisuje sadržaj direktorija i svih poddirektorija.",
                "Komanda prima jedan argument - putanju do direktorija.");
    }

}
