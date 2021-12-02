package com.labs.lab6.Server.Utils;

import com.labs.lab6.common.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RequestsDeserializator {

    public Request deserializeRequest(byte[] buffer) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            Request obj = (Request) inputStream.readObject();

            inputStream.close();
            return obj;

        } catch(ClassNotFoundException e) {
            RepliesSendler.sendReply("Class not found. " + e.getMessage());
        } catch (IOException e) {
            RepliesSendler.sendReply(e.getMessage());
        }

        return null;
    }

}
