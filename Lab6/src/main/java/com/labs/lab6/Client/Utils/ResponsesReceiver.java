package com.labs.lab6.Client.Utils;

import com.labs.lab6.Client.App.Main;
import com.labs.lab6.common.Exceptions.TimeoutException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import java.util.Set;

public class ResponsesReceiver {

    private final short BUFFER_SIZE; // unsigned value
    private final int PORT;
    private final long TIMEOUT;

    public ResponsesReceiver(int PORT, short BUFFER_SIZE, long TIMEOUT) {
        this.PORT = PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.TIMEOUT = TIMEOUT;
    }


    public String getResponse() {

        try (DatagramChannel channel = DatagramChannel.open()){
            Selector selector = Selector.open();
            channel.configureBlocking(false);

            DatagramSocket socket = channel.socket();

            // RequestSendler uses PORT so increment solve this problem
            socket.bind(new InetSocketAddress(PORT+1));

            channel.register(selector, SelectionKey.OP_READ);

            long start_time = System.currentTimeMillis();
            while (true) {

                if (System.currentTimeMillis() - start_time > TIMEOUT) {
                    selector.close();
                    throw new TimeoutException("Server not available at the moment: Timed out");
                }

                if (selector.selectNow() > 0) {
                    Set<SelectionKey> selectionKeys = selector.keys();
                    for (SelectionKey selectionKey : selectionKeys) {
                        if (selectionKey.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
                            byteBuffer.clear();

                            channel.receive(byteBuffer);
                            selector.close();

                            return new String(byteBuffer.array());
                        }
                    }
                }
            }
            

        } catch (TimeoutException e){
            ConsoleManager.replyUser(e.getMessage());
            return null;
        } catch (IOException e) {
            if (Main.getINIT()) ConsoleManager.replyUser("ERROR: Getting response failed. " + e.getMessage());
            else System.out.printf("Port %d is already busy\n", PORT);
            return null;
        }
    }

    public int getPort() {
        return PORT;
    }
    public short getBufferSize(){
        return BUFFER_SIZE;
    }
}
