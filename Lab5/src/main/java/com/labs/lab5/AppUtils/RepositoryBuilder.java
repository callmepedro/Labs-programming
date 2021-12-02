package com.labs.lab5.AppUtils;

import com.labs.lab5.App.Main;
import com.labs.lab5.AppObjects.SpaceMarine;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Class for building repository from XML file
 */
public class RepositoryBuilder {

    private int getMaxId(Repository repository){
        int maxId = 0;
        for (int i = 0; i < repository.size(); ++i) {
            int curId = repository.getList().get(i).getId();
            if (curId > maxId){
                maxId = curId;
            }
        }
        return maxId;
    }

    /**
     * Method for blocking XStream warnings
     */
    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Build repository from XML file by environment var
     * @return Repository from XML. For problems with reading XML returns empty Repository
     */
    public Repository buildFromXml() {
        disableWarning();
        XStream xstream = new XStream(new StaxDriver());
        try (InputStream inputStream =
                new BufferedInputStream(
                        new FileInputStream(Main.getFileName()))) {

            Repository repository = (Repository) xstream.fromXML(inputStream);
            SpaceMarine.setCounter(getMaxId(repository));
            return repository;

        } catch (IOException e) {
            ConsoleManager.replyUser("XML file reading failed. " + e.getMessage());
        } catch (RuntimeException e) {
            ConsoleManager.replyUser("XML file reading failed. Invalid format of XML file");
        }

        return new Repository(new ArrayList<>());
    }
}
