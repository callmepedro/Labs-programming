package com.labs.lab5.AppUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class for reading script commands from file
 */
public class ScriptFileReader {

    BufferedInputStream bf;

    public ScriptFileReader(String path) throws FileNotFoundException {
        bf = new BufferedInputStream(new FileInputStream(path));
    }

    /**
     * Read line from file
     * @return One line with instruction (at the end of file return null)
     * @throws IOException
     */
    public String nextLine() throws IOException {
        String line = "";
        int i;
        while ((i = bf.read()) != -1) {
            if (i == 10){
                return line;
            }
            char c = (char)i;
            line += c;
        }
        return null;
    }
}
