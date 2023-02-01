package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class HexdumpShellCommand implements ShellCommand {

    /**
     * Ispiše sadržaj datoteke u heksadekadskom obliku.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {

        try (
                InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(arguments)));) {
            byte[] buffer = new byte[16];
            int read = 0;
            int cnt = 0;
            while ((read = is.read(buffer)) != -1) {
                env.write(String.format("%08X: ", cnt));
                for (int i = 0; i < read; i++) {
                    env.write(String.format("%02X ", buffer[i]));
                }
                for (int i = read; i < 16; i++) {
                    env.write("   ");
                }
                env.write("|");
                for (int i = 0; i < read; i++) {
                    if (buffer[i] < 32 || buffer[i] > 127) {
                        env.write(".");
                    } else {
                        env.write(String.format("%c", buffer[i]));
                    }
                }
                env.write("|");
                env.writeln("");
                cnt += read;
            }
        } catch (IOException e) {
            env.writeln("Greška prilikom čitanja datoteke.");
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
        return "hexdump";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda hexdump ispisuje sadržaj datoteke u heksadekadskom obliku.");
    }

}
