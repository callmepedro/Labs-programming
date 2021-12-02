package com.labs.lab7.Client.Utils;

import com.labs.lab7.Client.App.Main;
import com.labs.lab7.common.Exceptions.TimeoutException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

import java.util.Set;

public class RequestsReceiver {

    private final short BUFFER_SIZE; // unsigned value
    private final int CLIENT_PORT;
    private final long TIMEOUT;

    public RequestsReceiver(int CLIENT_PORT, short BUFFER_SIZE, long TIMEOUT) {
        this.CLIENT_PORT = CLIENT_PORT;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.TIMEOUT = TIMEOUT;
    }

    public String getRequest() {

        try (DatagramChannel channel = DatagramChannel.open()){
            Selector selector = Selector.open();
            channel.configureBlocking(false);

            DatagramSocket socket = channel.socket();

            socket.bind(new InetSocketAddress(CLIENT_PORT));

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
            else System.out.println("Access denied. Change port or/and address");
            return null;
        }

//        try(ServerSocketChannel channel = ServerSocketChannel.open()) {
//            channel.bind(new InetSocketAddress(PORT+1));
//
//            SocketChannel socketChannel = channel.accept();
//            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
//            socketChannel.read(byteBuffer);
//
//            return new String(byteBuffer.array());
//
//        } catch (IOException e) {
//            if (Main.getINIT())
//                ConsoleManager.replyUser("ERROR: Getting response failed. " + e.getMessage());
//            else
//                System.out.println("Access denied. Change port or/and address ");
//            return null;
//        }
    }

}
