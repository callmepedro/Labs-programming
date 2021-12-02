package com.labs.lab7.Server.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String DRIVER = "org.postgresql.Driver";
    private static String URL = "jdbc:postgresql://localhost:5432/callmepedro";

    public static Connection getConnection(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("bd.properties"));
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, properties);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection with data base error");
            return null;
        } catch (FileNotFoundException e){
            System.out.println("File bd.properties not found");
            return null;
        } catch (IOException e){
            System.out.println("IOE");
            return null;
        }
    }
}