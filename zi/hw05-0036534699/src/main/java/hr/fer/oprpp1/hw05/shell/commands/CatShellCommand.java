package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CatShellCommand implements ShellCommand {

    /**
     * Ispisuje sadržaj datoteke.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {
        if (arguments == null) {
            env.writeln("Komanda cat prima jedan ili dva argumenta.");
            return ShellStatus.CONTINUE;
        }
        String[] args = ArgumentSplitter.splitArguments(arguments);
        if (args.length > 2) {
            env.writeln("Komanda cat prima jedan ili dva argumenta.");
            return ShellStatus.CONTINUE;
        }
        if (args.length == 1) {
            try (FileInputStream fis = new FileInputStream(args[0]);
                    InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset());
                    BufferedReader reader = new BufferedReader(isr)) {

                String line;

                while ((line = reader.readLine()) != null) {
                    env.writeln(line);
                }

            } catch (Exception e) {
                env.writeln("Ne mogu otvoriti datoteku " + args[0]);
                return ShellStatus.CONTINUE;
            }

            return ShellStatus.CONTINUE;
        } else { // 2 arguments

            try (FileInputStream fis = new FileInputStream(args[0]);
                    InputStreamReader isr = new InputStreamReader(fis, Charset.forName(args[1]));
                    BufferedReader reader = new BufferedReader(isr)) {

                String line;

                while ((line = reader.readLine()) != null) {
                    env.writeln(line);
                }

            } catch (Exception e) {
                env.writeln("Ne mogu otvoriti datoteku " + args[0] + " sa charsetom " + args[1]);
                return ShellStatus.CONTINUE;
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
        return "cat";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(
                "Komanda cat prima jedan ili dva argumenta.",
                "Prvi argument je ime datoteke koju treba ispisati na standardni izlaz.",
                "Drugi argument je opcionalan i ako je naveden, onda predstavlja charset kojim se datoteka interpretira.");

    }

}
