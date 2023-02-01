package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

/**
 * Razred koji predstavlja implementaciju sučelja Enviroment.
 * 
 */
public class EnviromentImpl implements Enviroment {

    private SortedMap<String, ShellCommand> commands = new java.util.TreeMap<>();

    Character promptSymbol = '>';
    Character multilineSymbol = '|';
    Character morelinesSymbol = '\\';

    public EnviromentImpl() {
        commands.put("cat", new CatShellCommand());
        commands.put("charsets", new CharsetsShellCommand());
        commands.put("copy", new CopyShellCommand());
        commands.put("hexdump", new HexdumpShellCommand());
        commands.put("exit", new ExitShellCommand());
        commands.put("ls", new LsShellCommand());
        commands.put("mkdir", new MkdirShellCommand());
        commands.put("tree", new TreeShellCommand());
        commands.put("symbol", new SymbolCommand());
        commands.put("help", new HelpCommand());
    }

    /**
     * Metoda koja cita liniju iz konzole.
     * 
     * @return String
     * @throws ShellIOException
     */
    @Override
    public String readLine() throws ShellIOException {
        try {
            String line = System.console().readLine();

            while (line.endsWith(morelinesSymbol.toString())) {
                line = line.substring(0, line.length() - 1);
                System.out.print(multilineSymbol + " ");
                line += System.console().readLine();
            }

            return line;
        } catch (Exception e) {
            throw new ShellIOException("Error while reading line.");
        }
    }

    /**
     * Metoda koja ispisuje tekst na konzolu.
     * 
     * @param text
     * @throws ShellIOException
     */
    @Override
    public void write(String text) throws ShellIOException {
        try {
            System.out.print(text);
        } catch (Exception e) {
            throw new ShellIOException("Error while writing.");
        }
    }

    /**
     * Metoda koja ispisuje tekst na konzolu uz novi red na kraju.
     * 
     * @param text
     * @throws ShellIOException
     */
    @Override
    public void writeln(String text) throws ShellIOException {
        try {
            System.out.println(text);
        } catch (Exception e) {
            throw new ShellIOException("Error while writing.");
        }
    }

    /**
     * Vraća mapu naredbi.
     * 
     * @return SortedMap<String, ShellCommand>
     */
    @Override
    public SortedMap<String, ShellCommand> commands() {
        SortedMap<String, ShellCommand> map = Collections.unmodifiableSortedMap(commands);

        return map;
    }

    /**
     * Vraca trenutni multiline symbol.
     * 
     * @return Character
     */
    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    /**
     * Postavlja novi multiline symbol.
     * 
     * @param symbol
     */
    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }

    /**
     * Vraca trenutni prompt symbol.
     * 
     * @return Character
     */
    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    /**
     * Postavlja novi prompt symbol.
     * 
     * @param symbol
     */
    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    /**
     * Vraća trenutni morelines symbol.
     * 
     * @return Character
     */
    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    /**
     * Postavlja novi morelines symbol.
     * 
     * @param symbol
     */
    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }

}
