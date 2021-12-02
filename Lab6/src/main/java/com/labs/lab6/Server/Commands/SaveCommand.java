package com.labs.lab6.Server.Commands;

import com.labs.lab6.Server.App.Main;
import com.labs.lab6.Server.Utils.Repository;
import com.labs.lab6.common.Request;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.*;
import static com.labs.lab6.Server.Utils.RepositoryBuilder.disableWarning;


/**
 * Save repository to XML file (File name is in environment variable)
 */
public class SaveCommand extends AbstractCommand{

    private final Repository repository;
    private String lastData;

    public SaveCommand(Repository repository) {
        super("save", "save repository to XML");
        this.repository = repository;
        this.lastData = "";
    }

    public String getLastData() {
        return lastData;
    }

    @Override
    public boolean execute(Request request) {  // response equals null by default
        disableWarning();
        XStream xstream = new XStream(new StaxDriver());

        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new BufferedOutputStream(
                        new FileOutputStream(Main.getFileName(), true)))) {

            PrintWriter printWriter = new PrintWriter(Main.getFileName());
            printWriter.close();

            xstream.toXML(repository, outputStreamWriter);

        } catch (IOException e) {
            lastData = "XML file writing failed";
            return false;
        }

        return true;
    }
}