package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Razred koji predstavlja glavni program shell-a.
 * 
 */
public class MyShell {

    public static void main(String[] args) {
        System.out.println("Welcome to MyShell v 1.0");

        // commands.put("exit", new ExitShellCommand());
        // commands.put("ls", new LsShellCommand());

        Enviroment env = new EnviromentImpl();

        SortedMap<String, ShellCommand> commands = env.commands();

        ShellStatus status = ShellStatus.CONTINUE;

        do {
            env.write(env.getPromptSymbol() + " ");
            String line = env.readLine();
            String commandName = line.split(" ")[0];
            String arguments = line.substring(commandName.length()).trim();

            ShellCommand command = commands.get(commandName);

            if (command == null) {
                env.writeln("Command " + commandName + " not found.");
                continue;
            }

            status = command.executeCommand(env, arguments);

            // System.out.println(commandName);

            // status = command.executeCommand(env, arguments);
        } while (status != ShellStatus.TERMINATE);
    }
}
