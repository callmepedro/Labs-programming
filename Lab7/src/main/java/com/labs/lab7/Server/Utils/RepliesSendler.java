package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.App.Main;
import com.labs.lab7.common.InitData;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RepliesSendler {

    private final InitData initData;

    public RepliesSendler(InitData initData) {
        this.initData = initData;
    }

    public InitData getInitData() {
        return initData;
    }

    public boolean sendReply(String msg) {
        byte[] buffer = msg.getBytes();

        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                    new InetSocketAddress(initData.getAddress(), initData.getPort()));

            Thread.sleep(10);

            socket.send(packet);
            return true;

        } catch (SocketException e) {
            System.out.println("Socket exception. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException. " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Troubles with Thread... " + e.getMessage());
        }
        return false;

//        byte[] buffer = msg.getBytes();
//
//        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress(Main.getDefaultAddr(), Main.getDefaultPort()+1))) {
//            Thread.sleep(10);
//
//            ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
//            byteBuffer.put(buffer);
//            byteBuffer.flip();
//            channel.write(byteBuffer);
//            return true;
//
//        } catch (SocketException e) {
//            System.out.println("RepliesSendler: Socket exception. " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("RepliesSendler: IOException. " + e.getMessage());
//        } catch (InterruptedException e) {
//            System.out.println("RepliesSendler: Troubles with Thread... " + e.getMessage());
//        }
//
//        return false;
    }

}
