package com.labs.lab6.Client.Utils;

import com.labs.lab6.common.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequestsSerializator {

    private final short bufferSize; // unsigned value

    public RequestsSerializator(short bufferSize) {
        this.bufferSize = bufferSize;
    }


    /**
     * Class for serialization requests before sending
     * @return serialized request ready to sent
     */
    public byte[] serializeRequest(Request request) throws IOException{

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(bufferSize);
        ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);

        outputStream.writeObject(request);
        byte[] obj = arrayOutputStream.toByteArray();

        outputStream.close();
        arrayOutputStream.close();

        return obj;
    }
}
