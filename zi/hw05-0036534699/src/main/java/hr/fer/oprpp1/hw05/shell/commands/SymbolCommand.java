package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class SymbolCommand implements ShellCommand {

    /**
     * Izvršava naredbu.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {
        String[] args = ArgumentSplitter.splitArguments(arguments);

        if (args.length == 2) {
            switch (args[0]) {
                case "PROMPT":
                    env.setPromptSymbol(args[1].charAt(0));
                    env.writeln("Symbol za " + args[0] + " postavljen na '" + args[1] + "'");
                    break;
                case "MORELINES":
                    env.setMorelinesSymbol(args[1].charAt(0));
                    env.writeln("Symbol za " + args[0] + " postavljen na '" + args[1] + "'");
                    break;
                case "MULTILINE":
                    env.setMultilineSymbol(args[1].charAt(0));
                    env.writeln("Symbol za " + args[0] + " postavljen na '" + args[1] + "'");
                    break;
                default:
                    env.writeln("Neispravan tip simbola.");
                    break;
            }
        } else if (args.length == 1) {
            switch (args[0]) {
                case "PROMPT":
                    env.writeln("Symbol za " + args[0] + " je '" + env.getPromptSymbol() + "'");
                    break;
                case "MORELINES":
                    env.writeln("Symbol za " + args[0] + " je '" + env.getMorelinesSymbol() + "'");
                    break;
                case "MULTILINE":
                    env.writeln("Symbol za " + args[0] + " je '" + env.getMultilineSymbol() + "'");
                    break;
                default:
                    env.writeln("Neispravan tip simbola.");
                    break;
            }
        } else {
            env.writeln("Komanda symbol prima 1 ili 2 argumenta.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Vraća ime naredbe.
     * 
     * @return String
     */
    @Override
    public String getCommandName() {
        return "symbol";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda symbol postavlja novi simbol za određeni tip.");
    }

}
