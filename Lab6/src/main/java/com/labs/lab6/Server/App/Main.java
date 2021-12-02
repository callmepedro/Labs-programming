package com.labs.lab6.Server.App;

import com.labs.lab6.Server.Utils.*;


public class Main {

    private static final int DEFAULT_PORT = 4114;
    private static final short DEFAULT_BUFFER_SIZE = 8192; // unsigned value
    private static final String DEFAULT_ADDR = "localhost";


    public static void main(String[] args) {

        RepositoryBuilder repositoryBuilder = new RepositoryBuilder();
        Repository repository = repositoryBuilder.buildFromXml();

        RequestsDeserializator deserializator = new RequestsDeserializator();
        RequestsReceiver receiver = new RequestsReceiver(DEFAULT_PORT, DEFAULT_BUFFER_SIZE, deserializator);

        CommandHandler handler = new CommandHandler(repository);
        ServerSession session = new ServerSession(receiver, handler);

        session.listen();
    }

    private static final String envFileName = "FILENAME_VAR";

    public static String getFileName() {
        return System.getenv(envFileName);
    }

    public static int getDefaultPort(){
        return DEFAULT_PORT;
    }
    public static String getDefaultAddr(){
        return DEFAULT_ADDR;
    }

}
