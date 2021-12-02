package com.labs.lab7.Server.App;

import com.labs.lab7.Server.Utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    private static int PORT = 4111;
    private static short BUFFER_SIZE = 8192; // unsigned value
    private static String ADDR = "localhost";


    public static void main(String[] args) {

        //RepositoryBuilder repositoryBuilder = new RepositoryBuilder();
        //Repository repository = repositoryBuilder.buildFromXml();
        try {

            RequestsDeserializator deserializator = new RequestsDeserializator();
            ResponseReceiver receiver = new ResponseReceiver(deserializator);

            Connection connection = DatabaseConnection.getConnection();
            DatabaseManager databaseManager = new DatabaseManager(connection);

            ServerSession session = new ServerSession(receiver, databaseManager);

            session.listen();

            assert connection != null;
            connection.close();

        } catch (SQLException e){
            System.out.println("Main: " + e.getMessage());
        }
    }

    private static final String envFileName = "FILENAME_VAR";
    private static final String userDataFileName = "users_data.xml";

    public static String getFileName() {
        return System.getenv(envFileName);
    }

    public static String getUserDataFileName() {
        return userDataFileName;
    }

    public static int getPort(){
        return PORT;
    }

    public static String getAddr(){
        return ADDR;
    }

    public static short getBufferSize(){
        return BUFFER_SIZE;
    }

    public static void setPort(int port) {
        PORT = port;
    }

    public static void setAddr(String addr){
        ADDR = addr;
    }

    public static void setBufferSize(short bufferSize){
        BUFFER_SIZE = bufferSize;
    }
}
