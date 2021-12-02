package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.App.Main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.labs.lab7.Server.Utils.RepositoryBuilder.disableWarning;

public class UserIdentification {
    private HashMap<String, String> usersConfidentData;
    private final ReadWriteLock lock;

    public UserIdentification(boolean xmlLoader){
        this.lock = new ReentrantReadWriteLock();
        this.usersConfidentData = new HashMap<>();

        if (xmlLoader) buildFromXml(Main.getUserDataFileName());
    }

    public boolean buildFromXml(String filename) {
        disableWarning();
        XStream xstream = new XStream(new StaxDriver());

        lock.writeLock().lock();
        try (InputStream inputStream =
                     new BufferedInputStream(
                             new FileInputStream(filename))) {

            usersConfidentData = (HashMap<String, String>) xstream.fromXML(inputStream);
            return true;

        } catch (IOException e) {
            System.out.println("Users data reading failed. " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Users data reading failed. Invalid format of XML file");
        } finally {
            lock.writeLock().unlock();
        }
        return false;
    }

    public boolean saveUsersDataToXML(String filename) {
        disableWarning();
        XStream xstream = new XStream(new StaxDriver());

        lock.writeLock().lock();
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new BufferedOutputStream(
                        new FileOutputStream(filename, true)))) {

            PrintWriter printWriter = new PrintWriter(filename);
            printWriter.close();

            xstream.toXML(usersConfidentData, outputStreamWriter);
            return true;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean regUser(String login, String password){
        if (usersConfidentData.containsKey(login))
            return false;
        usersConfidentData.put(login, password);
        return true;
    }

    public boolean authUser(String login, String password){
        String userPass = usersConfidentData.get(login);
        return userPass != null && userPass.equals(password);
    }
}
