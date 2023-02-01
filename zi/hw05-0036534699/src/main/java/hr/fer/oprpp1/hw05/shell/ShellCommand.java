package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Sučelje koje predstavlja naredbu koja se može izvršiti u shell-u.
 * 
 */
public interface ShellCommand {
    /**
     * Metoda koja izvršava naredbu.
     * 
     * @param env
     * @param arguments
     * @return status shell-a
     */
    ShellStatus executeCommand(Enviroment env, String arguments);

    /**
     * Metoda koja vraća ime naredbe.
     * 
     * @return ime naredbe
     */
    String getCommandName();

    /**
     * Metoda koja vraća opis naredbe.
     * 
     * @return opis naredbe
     */
    List<String> getCommandDescription();

}
