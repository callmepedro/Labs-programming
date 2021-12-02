package com.labs.lab7.Server.Utils;

import com.labs.lab7.common.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RequestsDeserializator {

    public Response deserializeRequest(byte[] buffer) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            Response obj = (Response) inputStream.readObject();

            inputStream.close();
            return obj;

        } catch(ClassNotFoundException e) {
            System.out.println("RequestDeserializator: ClassNotFoundException. " + e.getMessage());
            //RepliesSendler.sendReply("Class not found. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("RequestDeserializator: IOException. " + e.getMessage());
            //RepliesSendler.sendReply(e.getMessage());
        }

        return null;
    }

}
