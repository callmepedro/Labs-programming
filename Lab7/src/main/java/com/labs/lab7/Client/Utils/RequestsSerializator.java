package com.labs.lab7.Client.Utils;

import com.labs.lab7.common.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequestsSerializator {

    private final short BUFFER_SIZE = 4096; // unsigned value


    public byte[] serializeRequest(Response response) throws IOException{

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(BUFFER_SIZE);
        ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);

        outputStream.writeObject(response);
        byte[] obj= arrayOutputStream.toByteArray();

        outputStream.close();
        arrayOutputStream.close();

        return obj;
    }
}
