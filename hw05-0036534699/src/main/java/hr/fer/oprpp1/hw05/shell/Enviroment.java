package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Sučelje koje predstavlja okruženje u kojem se izvršava shell.
 * 
 */
public interface Enviroment {

    /**
     * Metoda koja čita jednu liniju iz okruženja.
     * 
     * @return linija
     * @throws ShellIOException
     */
    String readLine() throws ShellIOException;

    /**
     * Metoda koja ispisuje tekst u okruženje.
     * 
     * @param text
     * @throws ShellIOException
     */
    void write(String text) throws ShellIOException;

    /**
     * Metoda koja ispisuje tekst u okruženje uz novi redak.
     * 
     * @param text
     * @throws ShellIOException
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Metoda koja vraća mapu naredbi.
     * 
     * @return mapa naredbi
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Metoda koja vraća simbol za više redaka.
     * 
     * @return simbol
     */
    Character getMultilineSymbol();

    /**
     * Metoda koja postavlja simbol za više redaka.
     * 
     * @param symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Metoda koja vraća simbol za unos.
     * 
     * @return simbol
     */
    Character getPromptSymbol();

    /**
     * Metoda koja postavlja simbol za unos.
     * 
     * @param symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Metoda koja vraća simbol korisnika za dodatni redak.
     * 
     * @return simbol
     */
    Character getMorelinesSymbol();

    /**
     * Metoda koja postavlja simbol korisnika za dodatni redak.
     * 
     * @param symbol
     */
    void setMorelinesSymbol(Character symbol);
}
