package com.labs.lab5.AppUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class split input into command structures
 */
public class CommandReader {

    private static CommandReaderMode commandReaderMode = CommandReaderMode.CONSOLE;
    private static final Scanner scanner = new Scanner(System.in);
    private ScriptFileReader scriptFileReader = null;
    private String filePath = null;

    private static CommandStruct readCommandFromConsole() {
        return getCommandStruct(scanner.nextLine());
    }

    private static CommandStruct readCommandFromFile(ScriptFileReader scriptFileReader) throws IOException {
        return getCommandStruct(scriptFileReader.nextLine());
    }

    private static CommandStruct getCommandStruct(String s) {
        if (s == null) return new CommandStruct();

        s = s.trim();
        String[] parsedStrCommand = s.split(" ", 2);

        if (parsedStrCommand.length == 0 || s.equals("")){
            return new CommandStruct();
        }
        if (parsedStrCommand.length == 1){
            return new CommandStruct(parsedStrCommand[0].trim());
        }
        if (parsedStrCommand.length == 2){
            return new CommandStruct(parsedStrCommand[0].trim(), parsedStrCommand[1].trim());
        }
        return new CommandStruct();
    }

    /**
     * Turns to console input mod
     */
    public void setConsoleMod() {
        commandReaderMode = CommandReaderMode.CONSOLE;
    }

    /**
     * Turns to file input mod
     * @param path Path to the file
     * @throws FileNotFoundException
     */
    public void setFileMod(String path) throws FileNotFoundException {
        filePath = path;
        scriptFileReader = new ScriptFileReader(filePath);
        commandReaderMode = CommandReaderMode.FILE;
    }

    /**
     * Get current CommandReader mod
     * @return CommandReader mod
     */
    public CommandReaderMode getCommandReaderMod(){
        return commandReaderMode;
    }

    /**
     * Get path to the current file
     * @return Path to the file
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Read command according to current CommandReader mod
     * @return Parsed input to command struct
     * @throws IOException
     */
    public CommandStruct readCommand() throws IOException {
        if (commandReaderMode == CommandReaderMode.CONSOLE){
            return readCommandFromConsole();
        }
        else{
            return readCommandFromFile(scriptFileReader);
        }
    }
}
