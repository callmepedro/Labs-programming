package com.labs.lab7.Server.Commands;

import com.labs.lab7.Server.App.Main;
import com.labs.lab7.Server.Utils.Repository;
import com.labs.lab7.common.Response;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.labs.lab7.Server.Utils.RepositoryBuilder.disableWarning;


/**
 * Save repository to XML file (File name is in environment variable)
 */
public class SaveCommand extends AbstractCommand{

    private final Repository repository;
    private String lastData;
    private final ReadWriteLock lock;

    public SaveCommand(Repository repository) {
        super("save", "save repository to XML");
        this.repository = repository;
        this.lastData = "";
        this.lock = new ReentrantReadWriteLock();
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Response response) {  // response equals null by default
        disableWarning();
        XStream xstream = new XStream(new StaxDriver());

        lock.writeLock().lock();
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new BufferedOutputStream(
                        new FileOutputStream(Main.getFileName(), true)))) {

            PrintWriter printWriter = new PrintWriter(Main.getFileName());
            printWriter.close();

            xstream.toXML(repository, outputStreamWriter);

        } catch (IOException e) {
            lastData = "XML file writing failed";
            return false;

        } finally {
            lock.writeLock().unlock();
        }

        return true;
    }
}