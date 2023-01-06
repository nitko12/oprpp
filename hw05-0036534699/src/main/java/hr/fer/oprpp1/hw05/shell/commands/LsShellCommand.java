package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.oprpp1.hw05.shell.ArgumentSplitter;
import hr.fer.oprpp1.hw05.shell.Enviroment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class LsShellCommand implements ShellCommand {

    /**
     * Izlistava sadržaj direktorija.
     * 
     * @param env
     * @param arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Enviroment env, String arguments) {

        if (ArgumentSplitter.splitArguments(arguments).length != 1) {
            env.writeln("Komanda ls prima 1 argument.");
            return ShellStatus.CONTINUE;
        }

        try (Stream<Path> stream = Files.list(Paths.get(arguments))) {

            for (Path path : stream.toArray(Path[]::new)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
                        LinkOption.NOFOLLOW_LINKS);

                BasicFileAttributes attributes = faView.readAttributes();

                FileTime fileTime = attributes.creationTime(); // baca IOException pa sam morao for petlju mjesto
                                                               // streama

                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

                StringBuilder sb = new StringBuilder();

                sb.append(Files.isDirectory(path) ? "d" : "-");
                sb.append(Files.isReadable(path) ? "r" : "-");
                sb.append(Files.isWritable(path) ? "w" : "-");
                sb.append(Files.isExecutable(path) ? "x" : "-");
                sb.append(" " + String.format("%10d", attributes.size()));
                sb.append(" " + formattedDateTime);
                sb.append(" " + path.getFileName());

                env.writeln(sb.toString());
            }

        } catch (Exception e) {
            env.writeln("Ne mogu dohvatiti sadržaj direktorija " + arguments);
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
        return "ls";
    }

    /**
     * Vraća opis naredbe.
     * 
     * @return List<String>
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of("Komanda ls ispisuje sadržaj direktorija prvog argumenta.");
    }

}
