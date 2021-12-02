package com.labs.lab6.Client.Utils;

import com.labs.lab6.Client.App.Main;
import com.labs.lab6.common.Request;

import static com.labs.lab6.Client.Utils.ConsoleManager.*;
import java.io.IOException;
import java.net.*;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;

public class RequestsSendler {

    private final RequestsSerializator serializator;
    private final String ADDR;
    private final int PORT;
    private final short BUFFER_SIZE;

    public RequestsSendler(RequestsSerializator serializator, String ADDR, int PORT, short BUFFER_SIZE){
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

    public boolean sendRequest(Request request) {

        byte[] buffer;
        try {
            buffer = serializator.serializeRequest(request);
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
        } catch (BufferOverflowException e){
            replyUser("Buffer overflow. Try to increase BUFFER_SIZE.");
        } catch (IOException e) {
            if (Main.getINIT()) replyUser("ERROR: Request didn't send. " + e.getMessage());
        }

        return false;
    }
}
