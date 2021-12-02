package com.labs.lab6.Server.Utils;

import com.labs.lab6.Server.App.Main;
import com.labs.lab6.common.Request;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RequestsReceiver {

    private final int PORT;
    private final short BUFFER_SIZE; // unsigned value
    private final RequestsDeserializator deserializator;


    public RequestsReceiver(int PORT, short BUFFER_SIZE, RequestsDeserializator deserializator) {
        this.PORT = PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.deserializator = deserializator;
    }


    public Request getResponse() {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];

            DatagramSocket socket = new DatagramSocket(Main.getDefaultPort());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);


            socket.receive(packet);

            return deserializator.deserializeRequest(buffer);

        } catch (IOException e){
            //System.out.println("ResponseReceiver: " + e.getMessage());
            return null;
        }
    }
}
