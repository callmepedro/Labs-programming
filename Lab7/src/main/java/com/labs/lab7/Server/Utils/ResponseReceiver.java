package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.App.Main;
import com.labs.lab7.common.Response;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;

public class ResponseReceiver {

    private final RequestsDeserializator deserializator;
    int n = 0;


    public ResponseReceiver(RequestsDeserializator deserializator) {
        this.deserializator = deserializator;
    }


    public synchronized Response getResponse() {
        try {
            byte[] buffer = new byte[Main.getBufferSize()];

            DatagramSocket socket = new DatagramSocket(Main.getPort());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);


            socket.receive(packet);
            socket.close();

            return deserializator.deserializeRequest(buffer);

        } catch (IOException e){
            System.out.println("ResponseReceiver: " + e.getMessage());
            return null;
        }

//        try(ServerSocketChannel channel = ServerSocketChannel.open()) {
//            channel.bind(new InetSocketAddress(PORT));
//
//            SocketChannel socketChannel = channel.accept();
//            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
//            socketChannel.read(byteBuffer);
//
//            return deserializator.deserializeRequest(byteBuffer.array());
//
//        } catch (IOException e){
//            System.out.println("ResponseReceiver: IOException. " + e.getMessage());
//            return null;
//        }
    }
}
