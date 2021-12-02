package com.labs.lab7.Client.Utils;

import com.labs.lab7.Client.App.Main;
import com.labs.lab7.common.Response;

import static com.labs.lab7.Client.Utils.ConsoleManager.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;

public class ResponsesSendler {

    private final RequestsSerializator serializator;
    private final String ADDR;
    private final int PORT;
    private final short BUFFER_SIZE;

    public ResponsesSendler(RequestsSerializator serializator, String ADDR, int PORT, short BUFFER_SIZE){
        this.serializator = serializator;
        this.ADDR = ADDR;
        this.PORT = PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
    }

    public String getAddr(){
        return ADDR;
    }
    public int getPort() {
        return PORT;
    }
    public short getBufferSize(){
        return BUFFER_SIZE;
    }

    public boolean sendResponse(Response response) {

        byte[] buffer;
        try {
            buffer = serializator.serializeRequest(response);
        } catch (IOException e) {
            if (Main.getINIT()) replyUser("ERROR: Serialization failed. " + e.getMessage());
            return false;
        }

        try (DatagramChannel channel = DatagramChannel.open()) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            byteBuffer.clear();
            byteBuffer.put(buffer);
            byteBuffer.flip();

            SocketAddress socket = new InetSocketAddress(ADDR, PORT);
            channel.send(byteBuffer, socket);

            return true;

        } catch (UnresolvedAddressException e) {
            if (Main.getINIT()) replyUser("ERROR: Request didn't send. Check if the address is correct.");
        } catch (IllegalArgumentException e){
            replyUser(e.getMessage());
        } catch (IOException e) {
            if (Main.getINIT()) replyUser("ERROR: Request didn't send. " + e.getMessage());
        }

        return false;

//        byte[] buffer;
//        try {
//            buffer = serializator.serializeRequest(response);
//        } catch (IOException e) {
//            if (Main.getINIT()) replyUser("ERROR: Serialization failed. " + e.getMessage());
//            return false;
//        }
//
//        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress(ADDR, PORT))) {
//            ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
//            byteBuffer.put(buffer);
//            byteBuffer.flip();
//            channel.write(byteBuffer);
//            return true;
//
//        } catch (UnresolvedAddressException e) {
//            if (Main.getINIT()) replyUser("ERROR: Request didn't send. Check if the address is correct.");
//        } catch (IllegalArgumentException e){
//            replyUser(e.getMessage());
//        } catch (IOException e) {
//            if (Main.getINIT()) replyUser("ERROR: Request didn't send. " + e.getMessage());
//        }
//
//        return false;
    }
}
